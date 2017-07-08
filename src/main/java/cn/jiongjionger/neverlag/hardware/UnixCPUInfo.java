package cn.jiongjionger.neverlag.hardware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnixCPUInfo {

	public String getProcessorData() {
		List<String> streamProcessorInfo = HardwareInfoUtils.readFile("/proc/cpuinfo");
		final StringBuilder buffer = new StringBuilder();
		for (String line : streamProcessorInfo) {
			buffer.append(line).append("\r\n");
		}
		return buffer.toString();
	}

	public Map<String, String> parseInfo() {
		HashMap<String, String> processorDataMap = new HashMap<>();
		String[] dataStringLines = getProcessorData().split("\\r?\\n");
		for (final String dataLine : dataStringLines) {
			String[] dataStringInfo = dataLine.split(":");
			processorDataMap.put(dataStringInfo[0].trim(),
					(dataStringInfo.length == 2) ? dataStringInfo[1].trim() : "");
		}
		return processorDataMap;
	}
}
