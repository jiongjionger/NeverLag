package cn.jiongjionger.neverlag.hardware;

import java.util.Map;

public class WindowsCPUInfo {
	
	public Map<String, String> parseInfo() {
		Map<String, String> processorDataMap = WMI4Java.get().VBSEngine().getWMIObject(WMIClass.WIN32_PROCESSOR);
		String lineInfos = processorDataMap.get("Description");
		String[] infos = lineInfos.split("\\s+");
		processorDataMap.put("cpu family", infos[2]);
		processorDataMap.put("model", infos[4]);
		processorDataMap.put("stepping", infos[6]);
		processorDataMap.put("model name", processorDataMap.get("Name"));
		processorDataMap.put("cpu MHz", processorDataMap.get("MaxClockSpeed"));
		processorDataMap.put("vendor_id", processorDataMap.get("Manufacturer"));
		processorDataMap.put("cpu cores", processorDataMap.get("NumberOfCores"));
		return processorDataMap;
	}
}
