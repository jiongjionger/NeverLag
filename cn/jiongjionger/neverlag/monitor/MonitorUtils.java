package cn.jiongjionger.neverlag.monitor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import cn.jiongjionger.neverlag.monitor.inject.CommandInjector;
import cn.jiongjionger.neverlag.monitor.inject.EventExecutorInjector;
import cn.jiongjionger.neverlag.monitor.inject.SchedulerTaskInjector;

public class MonitorUtils {

	private static boolean enable = false;
	private static long enable_time;

	// 开启性能统计
	public static void enable() {
		enable = true;
		enable_time = System.currentTimeMillis();
		for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if (plugin != null && plugin.isEnabled()) {
				EventExecutorInjector.inject(plugin);
				SchedulerTaskInjector.inject(plugin);
				CommandInjector.inject(plugin);
			}
		}
	}

	// 关闭性能统计
	public static void disable() {
		enable = false;
		for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if (plugin != null && plugin.isEnabled()) {
				EventExecutorInjector.uninject(plugin);
				SchedulerTaskInjector.uninject(plugin);
				CommandInjector.uninject(plugin);
			}
		}
	}

	public static boolean isEnable() {
		return enable;
	}

	public static long getEnableTime() {
		return enable_time;
	}

}
