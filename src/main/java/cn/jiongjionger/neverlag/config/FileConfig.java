package cn.jiongjionger.neverlag.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.util.Objects;

// FileConfig来自喵呜的开源项目，美滋滋
public class FileConfig extends YamlConfiguration {

	protected File file;
	protected Logger logger;
	protected Plugin plugin;
	protected final DumperOptions yamlOptions = new DumperOptions();
	protected final Representer yamlRepresenter = new YamlRepresenter();
	protected final Yaml yaml = new Yaml(new YamlConstructor(), yamlRepresenter, yamlOptions);

	private FileConfig(File file) {
		Validate.notNull(file, "File cannot be null");
		this.file = file;
		logger = Bukkit.getLogger();
		init(file);
	}

	private FileConfig(InputStream stream) {
		logger = Bukkit.getLogger();
		init(stream);
	}

	public FileConfig(Plugin plugin, File file) {
		Validate.notNull(file, "File cannot be null");
		Validate.notNull(plugin, "Plugin cannot be null");
		this.plugin = plugin;
		this.file = file;
		logger = plugin.getLogger();
		check(file);
		init(file);
	}

	public FileConfig(Plugin plugin, String filename) {
		this(plugin, new File(plugin.getDataFolder(), filename));
	}

	private void check(File file) {
		String filename = file.getName();
		InputStream stream = plugin.getResource(filename);
		try {
			if (!file.exists()) {
				if (stream == null) {
					file.createNewFile();
					logger.info("Config file " + filename + " create failed!");
				} else {
					plugin.saveResource(filename, true);
				}
			} else {
				FileConfig newcfg = new FileConfig(stream);
				FileConfig oldcfg = new FileConfig(file);
				String newver = newcfg.getString("version");
				String oldver = oldcfg.getString("version");
				if (newver != null && Objects.equals(newver, oldver)) {
					logger.warning("Config file " + filename + " version " + oldver + " is too old and is upgrading to the " + newver + " version..");
					try {
						oldcfg.save(new File(file.getParent(), filename + ".backup"));
					} catch (IOException e) {
						logger.warning("Config file " + filename + " backup failed!");
					}
					plugin.saveResource(filename, true);
					logger.info("Config file " + filename + " upgrade success!");
				}
			}
		} catch (IOException e) {
			logger.info("Config file " + filename + " create failed!");
		}
	}

	private void init(File file) {
		Validate.notNull(file, "File cannot be null");
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
			init(stream);
		} catch (FileNotFoundException e) {
			logger.info("Config file " + file.getName() + " does not exist!");
		}
	}

	private void init(InputStream stream) {
		Validate.notNull(stream, "Stream cannot be null");
		try {
			this.load(new InputStreamReader(stream, Charsets.UTF_8));
		} catch (IOException ex) {
			logger.info("Config file " + file.getName() + " cannot touch!");
		} catch (InvalidConfigurationException ex) {
			logger.info("Config file " + file.getName() + " is wrong encoding format!");
		}
	}

	@Override
	public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
		Validate.notNull(file, "File cannot be null");
		final FileInputStream stream = new FileInputStream(file);
		load(new InputStreamReader(stream, Charsets.UTF_8));
	}

	public void load(Reader reader) throws IOException, InvalidConfigurationException {
		BufferedReader input = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
		StringBuilder builder = new StringBuilder();
		try {
			String line;
			while ((line = input.readLine()) != null) {
				builder.append(line);
				builder.append('\n');
			}
		} finally {
			input.close();
		}
		loadFromString(builder.toString());
	}

	public void reload() {
		init(file);
	}

	public void save() {
		try {
			this.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(File file) throws IOException {
		Validate.notNull(file, "File cannot be null");
		Files.createParentDirs(file);
		String data = saveToString();
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8)) {
			writer.write(data);
		}
	}

	@Override
	public String saveToString() {
		yamlOptions.setIndent(options().indent());
		yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		String header = buildHeader();
		String dump = yaml.dump(getValues(false));
		if (dump.equals(BLANK_CONFIG)) {
			dump = "";
		}
		return header + dump;
	}
}
