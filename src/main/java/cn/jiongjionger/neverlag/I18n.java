package cn.jiongjionger.neverlag;

import cn.jiongjionger.neverlag.utils.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class I18n extends ResourceBundle {
	public static String colorize(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
	// TODO: 增加自动对旧版的语言文件升级的功能
	public static I18n load(File directory, String locale) {
		Locale localeObj = StringUtils.toLocale(locale);
		File file = new File(directory, localeObj.toString().concat(".yml"));
		try(Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(reader);
			return new I18n(localeObj, config.getRoot());
		} catch (FileNotFoundException ex) {   // 本地找不到语言文件, 尝试在 jar 中寻找
			InputStream in = I18n.class.getResourceAsStream("/lang/" + file.getName());
			if(in == null) {  // jar中也不存在
				throw new RuntimeException("Language file \"" + file.getName() + "\" isn't exists!");
			}
			
			try {
				file.getParentFile().mkdirs();  // 复制jar中的语言文件到本地
				Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex1) {
				throw new RuntimeException(ex);
			}
			return load(directory, locale);   // 重试
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected final Locale locale;
	protected final Map<String, String> map;
	private final String namespace;
	
	private I18n(Locale locale, Map<String, String> map, String namespace) {
		this.locale = locale;
		this.map = map;
		this.namespace = namespace == null ? null : namespace.concat(".");
	}

	public I18n(Locale locale, Map<String, String> map) {
		this(locale, new HashMap<String, String>(), null);
		this.map.putAll(map);
	}

	public I18n(Locale locale, Configuration configuration) {
		this(locale, new HashMap<String, String>(), null);
		for(String key : configuration.getKeys(true)) {
			Object obj = configuration.get(key);
			if(obj instanceof CharSequence) {
				this.map.put(key, obj.toString());
			}
		}
	}
	
	/**
	 * 获取本地化文本. 
	 * <p>
	 * 如果 key 以 '/' 字符开头, 则作为绝对路径处理, 不使用命名空间. 
	 * @return 经本地化、{@linkplain ChatColor#translateAlternateColorCodes(char, String) 格式控制字符转换} 
	 *		与 {@linkplain MessageFormat#format(String, Object...) 格式化} 处理后的文本
	 */
	public String tr(String key, Object... obj) {
		try {
			String str = getString(key);
			return colorize(MessageFormat.format(str, obj));
		} catch (MissingResourceException ex) {
			return key;
		}
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式. 
	 * <p>
	 * 此方法与 {@link #tr(String, Object...)} 的区别在于
	 *	它不会进行 {@linkplain MessageFormat#format(String, Object...) 格式化处理}.
	 * @see #tr(String, Object...) 
	 */
	public String tr(String key) {
		try {
			String str = getString(key);
			return colorize(str);
		} catch (MissingResourceException ex) {
			return key;
		}
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式. 
	 * @see #tr(String, Object...) 
	 */
	public String tr(String key, Object o1) {
		return tr(key, new Object[]{ o1 });
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式. 
	 * @see #tr(String, Object...) 
	 */
	public String tr(String key, Object o1, Object o2) {
		return tr(key, new Object[]{ o1, o2 });
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式. 
	 * @see #tr(String, Object...) 
	 */
	public String tr(String key, Object o1, Object o2, Object o3) {
		return tr(key, new Object[]{ o1, o2, o3 });
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式. 
	 * @see #tr(String, Object...) 
	 */
	public String tr(String key, Object o1, Object o2, Object o3, Object o4) {
		return tr(key, new Object[]{ o1, o2, o3, o4 });
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	protected Object handleGetObject(String key) {
		/*
		 如果 key 以 '/' 字符开头则当作绝对路径处理, 不使用命名空间
		*/
		if(key.charAt(0) != '/') {
			if(namespace != null) {
				key = namespace.concat(key);
			}
		} else {
			key = key.substring(1);
		}
		return map.get(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(map.keySet());
	}

	/**
	 * clone 出一个具有指定命名空间的 I18n 实例. 
	 * <p>
	 * 此方法不会改变原有实例的状态. 
	 * @param namespace 要设定的命名空间
	 * @return 一个新的 I18n
	 */
	public I18n clone(String namespace) {
		I18n cloned = new I18n(locale, map, namespace);
		return cloned;
	}
}
