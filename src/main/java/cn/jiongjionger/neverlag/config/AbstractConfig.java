package cn.jiongjionger.neverlag.config;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author andylizi
 */
public abstract class AbstractConfig {

	protected Logger logger = Bukkit.getLogger();
	protected FileConfiguration config;
	protected Map<String, Field> fields;

	public AbstractConfig(FileConfiguration config) {
		this.config = config;
	}

	protected AbstractConfig(FileConfiguration config, Logger logger) {
		this(config);
		this.logger = logger;
	}

	public void load() {
		try {
			load0();
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Unable to load config", ex);
		}

		for (Entry<String, Field> entry : getConfigFields().entrySet()) {
			String key = entry.getKey();
			Field f = entry.getValue();
			try {
				Object def = f.get(this);
				if (!config.contains(key)) {
					config.addDefault(key, def);
					return;
				}
				Object v = loadData(key, def);
				if (!checkValue(key, v)) {
					logger.log(Level.WARNING, "Illegal value \"{0}\" of {1}, use default \"{2}\"!",
						new Object[]{v, key, def});
					return;
				}
				f.set(this, v);
			} catch (ReflectiveOperationException ex) {
				logger.log(Level.WARNING, "Unable to update config field:{0}", f.toGenericString());
				ex.printStackTrace();
			} catch (Exception ex) {
				logger.warning(key);
				ex.printStackTrace();
			}
		}
	}

	protected abstract void load0() throws IOException;

	protected Object loadData(String key, Object def) {
		return config.get(key, def);
	}

	public void save() {
		for (Entry<String, Field> entry : getConfigFields().entrySet()) {
			String key = entry.getKey();
			Field f = entry.getValue();
			try {
				saveData(key, f.get(this));
			} catch (ReflectiveOperationException ex) {
				logger.log(Level.WARNING, "Unable to update config field:{0}", f.toGenericString());
				ex.printStackTrace();
			}
		}

		try {
			save0();
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Unable to save config", ex);
		}
	}

	protected abstract void save0() throws IOException;

	protected void saveData(String key, Object value) {
		config.set(key, value);
	}

	protected boolean checkValue(String key, Object value) {
		return true;
	}

	public void reload() {
		load();
		save();
	}

	protected Map<String, Field> getConfigFields() {
		if (fields != null) {
			return fields;
		}

		Class<? extends AbstractConfig> clazz = getClass();
		Map<String, Field> map = new LinkedHashMap<>();
		for (Field field : clazz.getFields()) {
			F info = field.getAnnotation(F.class);
			if (info == null) {
				continue;
			}

			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
				logger.log(Level.WARNING, "Invalid config field: {0}", field.toGenericString());
				continue;
			}

			field.setAccessible(true);
			map.put(info.value(), field);
		}

		return fields = Collections.unmodifiableMap(map);
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	protected static @interface F {
		String value();
	}
}
