package cn.jiongjionger.neverlag.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

	public static boolean isEnable() {
		return enable;
	}

	public static long getEnableTime() {
		return enable_time;
	}

	/*
	 * 获取插件的事件耗时情况
	 * 
	 * @param plugin 插件
	 * 
	 * @return 插件耗时情况Map
	 */
	public static Map<String, Map<String, Long>> getEventUsagesByPlugin(Plugin plugin) {
		Map<String, Map<String, Long>> eventUsagesMap = new HashMap<String, Map<String, Long>>();
		if (plugin == null) {
			return eventUsagesMap;
		}
		for (RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
			try {
				FieldAccessor<EventExecutor> field = Reflection.getField(RegisteredListener.class, "executor", EventExecutor.class);
				EventExecutor executor = field.get(listener);
				if (executor instanceof EventExecutorInjector) {
					EventExecutorInjector eventExecutorInjector = (EventExecutorInjector) executor;
					// 总耗时
					if (eventUsagesMap.containsKey("totalTime")) {
						eventUsagesMap.put("totalTime", mergeMap(eventExecutorInjector.getTotalTime(), eventUsagesMap.get("totalTime")));
					} else {
						eventUsagesMap.put("totalTime", recordCount(eventExecutorInjector.getTotalTime().entrySet()));
					}
					// 执行次数
					if (eventUsagesMap.containsKey("totalCount")) {
						eventUsagesMap.put("totalCount", mergeMap(eventExecutorInjector.getTotalCount(), eventUsagesMap.get("totalCount")));
					} else {
						eventUsagesMap.put("totalCount", recordCount(eventExecutorInjector.getTotalCount().entrySet()));
					}
					// 最大执行时间
					if (eventUsagesMap.containsKey("maxExecuteTime")) {
						eventUsagesMap.put("maxExecuteTime", mergeMaxTimeMap(eventExecutorInjector.getMaxExecuteTime(), eventUsagesMap.get("maxExecuteTime")));
					} else {
						eventUsagesMap.put("maxExecuteTime", recordMaxTime(eventExecutorInjector.getMaxExecuteTime().entrySet()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return eventUsagesMap;
	}

	/*
	 * 通过插件名字获取插件的事件耗时情况
	 * 
	 * @param pluginname 插件名字
	 * 
	 * @return 插件耗时情况Map
	 */
	public static Map<String, Map<String, Long>> getEventUsagesByPluginName(String pluginname) {
		return getEventUsagesByPlugin(Bukkit.getPluginManager().getPlugin(pluginname));
	}

	// 计算事件执行时间、次数
	private static Map<String, Long> recordCount(Set<Entry<String, Long>> record) {
		Map<String, Long> usagesMap = new HashMap<String, Long>();
		for (Entry<String, Long> entry : record) {
			if (usagesMap.containsKey(entry.getKey())) {
				long totalTime = usagesMap.get(entry.getKey());
				usagesMap.put(entry.getKey(), totalTime + entry.getValue());
			} else {
				usagesMap.put(entry.getKey(), entry.getValue());
			}
		}
		return usagesMap;
	}

	// 计算事件最大执行时间
	private static Map<String, Long> recordMaxTime(Set<Entry<String, Long>> record) {
		Map<String, Long> usagesMap = new HashMap<String, Long>();
		for (Entry<String, Long> entry : record) {
			if (usagesMap.containsKey(entry.getKey())) {
				long time = usagesMap.get(entry.getKey());
				if (entry.getValue() > time) {
					usagesMap.put(entry.getKey(), entry.getValue());
				}
			} else {
				usagesMap.put(entry.getKey(), entry.getValue());
			}
		}
		return usagesMap;
	}

	// 合并执行时间/次数Map
	private static Map<String, Long> mergeMap(Map<String, Long> map1, Map<String, Long> map2) {
		Map<String, Long> usagesMap = new HashMap<String, Long>();
		usagesMap.putAll(map1);
		for (Entry<String, Long> entry : map2.entrySet()) {
			if (usagesMap.containsKey(entry.getKey())) {
				long value = usagesMap.get(entry.getKey());
				usagesMap.put(entry.getKey(), value + entry.getValue());
			} else {
				usagesMap.put(entry.getKey(), entry.getValue());
			}
		}
		return usagesMap;
	}

	// 合并最大执行时间Map
	private static Map<String, Long> mergeMaxTimeMap(Map<String, Long> map1, Map<String, Long> map2) {
		Map<String, Long> usagesMap = new HashMap<String, Long>();
		usagesMap.putAll(map1);
		for (Entry<String, Long> entry : map2.entrySet()) {
			if (usagesMap.containsKey(entry.getKey())) {
				long time = usagesMap.get(entry.getKey());
				if (entry.getValue() > time) {
					usagesMap.put(entry.getKey(), entry.getValue());
				}
			} else {
				usagesMap.put(entry.getKey(), entry.getValue());
			}
		}
		return usagesMap;
	}
}
