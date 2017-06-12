package cn.jiongjionger.neverlag.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

import cn.jiongjionger.neverlag.hardware.UnixCPUInfo;
import cn.jiongjionger.neverlag.hardware.UnixMemoryInfo;
import cn.jiongjionger.neverlag.hardware.WindowsCPUInfo;
import cn.jiongjionger.neverlag.hardware.WindowsMemoryInfo;

public class HardWareInfo {

	private static final String OS = System.getProperty("os.name").toLowerCase();
	private static RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	private static OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
	private static Map<String, String> cpuInfoMap = new HashMap<String, String>();

	public static boolean isWindows() {
		return OS.contains("win");
	}

	public static boolean isUnix() {
		return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
	}

	// 获取JVM名称和版本号
	public static String getJVMInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(runtimeMXBean.getVmName())
				.append(" (")
				.append(runtimeMXBean.getVmVersion())
				.append(")");
		return sb.toString();
	}

	// 获取JVM参数
	public static String getJVMArg() {
		StringBuilder sb = new StringBuilder();
		for (String arg : runtimeMXBean.getInputArguments()) {
			sb.append(arg).append(" ");
		}
		return sb.toString();
	}

	// 获取操作系统信息
	public static String getSystemInfo() {
		String arch = operatingSystemMXBean.getArch();
		if (arch.contains("64")) {
			arch = "x64";
		} else if (arch.contains("86")) {
			arch = "x86";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(operatingSystemMXBean.getName())
				.append(" version ")
				.append(operatingSystemMXBean.getVersion())
				.append(" ")
				.append(arch);
		return sb.toString();
	}

	// 获取CPU信息（类似Intel(R) Xeon(R) CPU E5-2679V4 @ 3.2GHz 20 cores B0 stepping）
	public static String getCPUInfo() {
		try {
			synchronized (cpuInfoMap) {
				if (isWindows()) {
					if (cpuInfoMap.isEmpty()) {
						WindowsCPUInfo cpuInfo = new WindowsCPUInfo();
						cpuInfoMap = cpuInfo.parseInfo();
					}
				} else if (isUnix()) {
					if (cpuInfoMap.isEmpty()) {
						UnixCPUInfo cpuInfo = new UnixCPUInfo();
						cpuInfoMap = cpuInfo.parseInfo();
					}
				} else {
					return null;
				}
				StringBuilder sb = new StringBuilder();
				sb.append(cpuInfoMap.get("model name"))
						.append(" ")
						.append(cpuInfoMap.get("cpu cores"))
						.append(" cores ")
						.append(cpuInfoMap.get("stepping"))
						.append(" stepping");
				return sb.toString();
			}
		} catch (Exception e) {
			return null;
		}
	}

	// 获取物理内存使用情况（使用量 / 总量）类似 109621MB / 262144MB
	public static String getMemoryInfo() {
		try {
			Map<String, String> memoryInfoMap = new HashMap<String, String>();
			if (isWindows()) {
				WindowsMemoryInfo memoryInfo = new WindowsMemoryInfo();
				memoryInfoMap = memoryInfo.parseInfo();
			} else if (isUnix()) {
				UnixMemoryInfo memoryInfo = new UnixMemoryInfo();
				memoryInfoMap = memoryInfo.parseInfo();
			} else {
				return null;
			}

			String totalMemory = "";
			String freeMemory = "";
			if (memoryInfoMap.get("MemTotal") != null) {
				totalMemory = String.valueOf(Long.parseLong(memoryInfoMap.get("MemTotal")) / 1024);
			} else {
				totalMemory = "Unknown";
			}
			if (memoryInfoMap.get("MemFree") != null) {
				freeMemory = String.valueOf(Long.parseLong(memoryInfoMap.get("MemFree")) / 1024);
			} else {
				freeMemory = "Unknown";
			}
			StringBuilder sb = new StringBuilder();
			sb.append(freeMemory).append("MB / ").append(totalMemory).append("MB");
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
