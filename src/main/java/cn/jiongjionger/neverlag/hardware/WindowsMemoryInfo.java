package cn.jiongjionger.neverlag.hardware;

import java.util.Map;

public class WindowsMemoryInfo {
	
	public Map<String, String> parseInfo() {
		Map<String, String> memoryDataMap = WMI4Java.get().VBSEngine().getWMIObject(WMIClass.WIN32_PERFFORMATTEDDATA_PERFOS_MEMORY);
		memoryDataMap.putAll(WMI4Java.get().VBSEngine().getWMIObject(WMIClass.WIN32_PHYSICALMEMORY));
		memoryDataMap.put("MemAvailable", memoryDataMap.get("AvailableKBytes"));
		memoryDataMap.put("MemFree", WMI4Java.get().VBSEngine().getWMIObject(WMIClass.WIN32_OPERATINGSYSTEM).get("FreePhysicalMemory"));
		memoryDataMap.put("MemTotal", WMI4Java.get().VBSEngine().getWMIObject(WMIClass.WIN32_OPERATINGSYSTEM).get("TotalPhysicalMemory"));
		return memoryDataMap;
	}
}
