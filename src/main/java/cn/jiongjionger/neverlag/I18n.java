package cn.jiongjionger.neverlag;

import cn.jiongjionger.neverlag.utils.NeverLagUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Level;

public class I18n extends ResourceBundle {
	public static String colorize(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static I18n load(File directory, String locale) throws FileNotFoundException {
		Locale localeObj = NeverLagUtils.toLocale(locale);
		File file = new File(directory, localeObj.toString().concat(".yml"));
		try (InputStream in = I18n.class.getResourceAsStream("/assets/lang/" + file.getName())) {
			if (!file.isFile()) {
				if (in != null) {
					try {
						file.getParentFile().mkdirs();
						Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING); // 复制jar中的语言文件到本地
					} catch (IOException ex) {
						throw new RuntimeException("Unable to extract " + file.getName(), ex);
					}
				} else {
					if (!localeObj.getCountry().isEmpty()) {
						String newLocale = new Locale(localeObj.getLanguage()).toString();
						NeverLag.logger().log(Level.INFO, "Language file {0}.yml not found, trying {1}.yml ...",
							new Object[]{ locale, newLocale });
						return load(directory, newLocale);
					}
					throw new FileNotFoundException("Language file " + file.getName() + " not found!");
				}
			}

			boolean needSave = false;
			FileConfiguration fileConfig = null;
			try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
				fileConfig = YamlConfiguration.loadConfiguration(reader);

				if (in != null) {   // 升级语言文件
					// loadConfiguration 方法会自动关闭传入的 Reader
					//noinspection IOResourceOpenedButNotSafelyClosed
					Reader jarReader = new InputStreamReader(in, StandardCharsets.UTF_8);
					Configuration jarConfig = YamlConfiguration.loadConfiguration(jarReader).getRoot();
					for (String key : jarConfig.getKeys(true)) {
						if (!fileConfig.contains(key)) {
							fileConfig.set(key, jarConfig.get(key));
							needSave = true;
						}
					}
				}

				return new I18n(localeObj, fileConfig.getRoot());
			} finally {
				if (needSave && fileConfig != null) {
					fileConfig.save(file);
				}
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new RuntimeException("Unable to load language file " + file.getName(), ex);
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
		for (String key : configuration.getKeys(true)) {
			Object obj = configuration.get(key);
			if (obj instanceof CharSequence) {
				this.map.put(key, obj.toString());
			}
		}
	}

	/**
	 * 获取本地化文本.
	 * <p>
	 * 如果 key 以 '/' 字符开头, 则作为绝对路径处理, 不使用命名空间.
	 *
	 * @return 经本地化、{@linkplain ChatColor#translateAlternateColorCodes(char, String) 格式控制字符转换}
	 * 与 {@linkplain MessageFormat#format(String, Object...) 格式化} 处理后的文本
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
	 * {@link #tr(String, Object...)} 的变体.
	 * <p>
	 * 此方法与 {@link #tr(String, Object...)} 的区别在于
	 * 它不会进行 {@linkplain MessageFormat#format(String, Object...) 格式化处理}.
	 *
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
	 *
	 * @see #tr(String, Object...)
	 */
	public String tr(String key, Object o1) {
		return tr(key, new Object[]{ o1 });
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式.
	 *
	 * @see #tr(String, Object...)
	 */
	public String tr(String key, Object o1, Object o2) {
		return tr(key, new Object[]{ o1, o2 });
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式.
	 *
	 * @see #tr(String, Object...)
	 */
	public String tr(String key, Object o1, Object o2, Object o3) {
		return tr(key, new Object[]{ o1, o2, o3 });
	}

	/**
	 * {@link #tr(String, Object...)} 的快捷方式.
	 *
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
		// 如果 key 以 '/' 字符开头则当作绝对路径处理, 不使用命名空间
		if (key.charAt(0) != '/') {
			if (namespace != null) {
				key = namespace.concat(key);
			}
		} else {
			key = key.substring(1);
		}
		return map.get(key);
	}

	/**
	 * clone 出一个具有指定命名空间的 I18n 实例.
	 * <p>
	 * 此方法不会改变原有实例的状态.
	 *
	 * @param namespace 要设定的命名空间
	 * @return 一个新的 I18n
	 */
	public I18n clone(String namespace) {
		return new I18n(locale, map, namespace);
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(map.keySet());
	}
}
