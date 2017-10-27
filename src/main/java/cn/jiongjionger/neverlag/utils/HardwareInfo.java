package cn.jiongjionger.neverlag.utils;

import cn.jiongjionger.neverlag.hardware.UnixCPUInfo;
import cn.jiongjionger.neverlag.hardware.UnixMemoryInfo;
import cn.jiongjionger.neverlag.hardware.WindowsCPUInfo;
import cn.jiongjionger.neverlag.hardware.WindowsMemoryInfo;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

public final class HardwareInfo {

	private static final String OS = System.getProperty("os.name").toLowerCase();
	private static final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	private static final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
	private static Map<String, String> cpuInfoMap = new HashMap<>();

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
				return String.format("%s %s cores %s stepping",
					cpuInfoMap.get("model name"),
					cpuInfoMap.get("cpu cores"),
					cpuInfoMap.get("stepping"));
			}
		} catch (Exception e) {
			return null;
		}
	}

	// 获取JVM参数
	public static String getJVMArg() {
		StringBuilder sb = new StringBuilder();
		for (String arg : runtimeMXBean.getInputArguments()) {
			sb.append(arg).append(" ");
		}
		return sb.toString();
	}

	// 获取JVM名称和版本号
	public static String getJVMInfo() {
		return runtimeMXBean.getVmName() + " (" + runtimeMXBean.getVmVersion() + ")";
	}

	// 获取物理内存使用情况（使用量 / 总量）类似 109621MB / 262144MB
	public static String getMemoryInfo() {
		try {
			Map<String, String> memoryInfoMap = new HashMap<>();
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
				// 针对WIN10无法获取物理内存总量的补救措施
				if (operatingSystemMXBean instanceof com.sun.management.OperatingSystemMXBean) {
					com.sun.management.OperatingSystemMXBean nativeSystemMXBean = (com.sun.management.OperatingSystemMXBean) operatingSystemMXBean;
					long nativeTotalMemory = nativeSystemMXBean.getTotalPhysicalMemorySize();
					if (nativeTotalMemory != 0L) {
						totalMemory = String.valueOf(nativeTotalMemory / 1024 / 1024);
					}
				} else {
					totalMemory = "Unknown";
				}
			}
			if (memoryInfoMap.get("MemFree") != null) {
				freeMemory = String.valueOf(Long.parseLong(memoryInfoMap.get("MemFree")) / 1024);
			} else {
				if (operatingSystemMXBean instanceof com.sun.management.OperatingSystemMXBean) {
					com.sun.management.OperatingSystemMXBean nativeSystemMXBean = (com.sun.management.OperatingSystemMXBean) operatingSystemMXBean;
					long nativeFreeMemory = nativeSystemMXBean.getFreePhysicalMemorySize();
					if (nativeFreeMemory != 0L) {
						freeMemory = String.valueOf(nativeFreeMemory / 1024 / 1024);
					}
				} else {
					freeMemory = "Unknown";
				}
			}
			return String.format("%sMB / %sMB", freeMemory, totalMemory);
		} catch (Exception e) {
			return null;
		}
	}

	// 获取操作系统信息
	public static String getSystemInfo() {
		String arch = operatingSystemMXBean.getArch();
		if (arch.contains("64")) {
			arch = "x64";
		} else if (arch.contains("86")) {
			arch = "x86";
		}

		return operatingSystemMXBean.getName() + " version " + operatingSystemMXBean.getVersion() + " " + arch;
	}

	public static boolean isUnix() {
		return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
	}

	public static boolean isWindows() {
		return OS.contains("win");
	}

	private HardwareInfo() {}
}
