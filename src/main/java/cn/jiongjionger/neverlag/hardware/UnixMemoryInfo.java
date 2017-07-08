package cn.jiongjionger.neverlag.hardware;

import java.util.HashMap;
import java.util.List;

public class UnixMemoryInfo {

	private String getMemoryData() {
		List<String> streamMemoryInfo = HardwareInfoUtils.readFile("/proc/meminfo");
		final StringBuilder buffer = new StringBuilder();
		for (String line : streamMemoryInfo) {
			buffer.append(line).append("\r\n");
		}
		return buffer.toString();
	}

	public HashMap<String, String> parseInfo() {
		HashMap<String, String> memoryDataMap = new HashMap<>();
		String[] dataStringLines = getMemoryData().split("\\r?\\n");
		for (final String dataLine : dataStringLines) {
			String[] dataStringInfo = dataLine.split(":");
			memoryDataMap.put(dataStringInfo[0].trim(), (dataStringInfo.length == 2) ? dataStringInfo[1].trim() : "");
		}
		return memoryDataMap;
	}
}
