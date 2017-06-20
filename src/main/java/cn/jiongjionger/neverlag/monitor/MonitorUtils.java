package cn.jiongjionger.neverlag.monitor;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import cn.jiongjionger.neverlag.monitor.inject.CommandInjector;
import cn.jiongjionger.neverlag.monitor.inject.EventExecutorInjector;
import cn.jiongjionger.neverlag.monitor.inject.SchedulerTaskInjector;
import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;

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
	
	/*
	public static void getEventTimings(Plugin plg) {
		for (RegisteredListener listener : HandlerList.getRegisteredListeners(plg)) {
			try {
				FieldAccessor<EventExecutor> field = Reflection.getField(RegisteredListener.class, "executor", EventExecutor.class);
				EventExecutor executor = field.get(listener);
				if (executor instanceof EventExecutorInjector) {
					EventExecutorInjector eventExecutorInjector = (EventExecutorInjector) executor;
					for (Entry<String, Long> entry:eventExecutorInjector.getAvgExecuteTime().entrySet()){
						Bukkit.getServer().broadcastMessage(entry.getKey() + " : " + entry.getValue());
					}
					Bukkit.getServer().broadcastMessage("==================");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	*/

	public static boolean isEnable() {
		return enable;
	}

	public static long getEnableTime() {
		return enable_time;
	}

}
