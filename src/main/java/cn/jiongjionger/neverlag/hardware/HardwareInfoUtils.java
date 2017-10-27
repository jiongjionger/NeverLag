package cn.jiongjionger.neverlag.hardware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HardwareInfoUtils {

	private static final String CRLF = "\r\n";
	private static final String NOT_FOUND = "NOT_FOUND";
	private static final Logger log = Logger.getLogger(HardwareInfoUtils.class.getName());

	public static String executeCommand(String... command) {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.redirectErrorStream(true);

			return readData(processBuilder.start());
		} catch (IOException ex) {
			Logger.getLogger(HardwareInfoUtils.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static String extractText(String text, String regex) {
		if (text.trim().isEmpty()) {
			return NOT_FOUND;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		if (matcher.groupCount() > 0) {
			return matcher.group(1);
		}
		return NOT_FOUND;
	}

	public static String getSingleValueFromFile(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			return reader.readLine();
		} catch (IOException ex) {
			return null;
		}
	}

	public static boolean isSudo() {
		return executeCommand("sudo", "-n", "true").isEmpty();
	}

	private static String readData(Process process) {
		StringBuilder commandOutput = new StringBuilder();
		BufferedReader processOutput = null;
		try {
			processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = processOutput.readLine()) != null) {
				if (!line.isEmpty()) {
					commandOutput.append(line).append(CRLF);
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(HardwareInfoUtils.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				if (processOutput != null) {
					processOutput.close();
				}
			} catch (IOException ioe) {
				Logger.getLogger(HardwareInfoUtils.class.getName()).log(Level.SEVERE, null, ioe);
			}
		}
		return commandOutput.toString();
	}

	public static List<String> readFile(String filePath) {
		Path path = Paths.get(filePath);
		List<String> fileLines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
			String line;
			while (null != (line = reader.readLine())) {
				fileLines.add(line);
			}
		} catch (IOException ex) {
			log.log(Level.SEVERE, "Unable to read file " + filePath, ex);
		}
		return fileLines;
	}

	public static String removeAllSpaces(String s) {
		return s.replaceAll("\\s+", "");
	}

	public static String toCamelCase(String s) {
		String[] parts = s.split("_");
		StringBuilder camelCaseString = new StringBuilder();
		for (String part : parts) {
			camelCaseString.append(toProperCase(part));
		}
		return camelCaseString.toString();
	}

	private static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	private HardwareInfoUtils() {
	}
}
