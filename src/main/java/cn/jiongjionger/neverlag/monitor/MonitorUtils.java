package cn.jiongjionger.neverlag.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.scheduler.BukkitTask;

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
	
	// 获取某个插件所有的事件耗时情况，同样的事件进行合并计算
	public static Map<String, MonitorRecord> getEventTimingsByPluginName(String name) {
		return getEventTimingsByPlugin(Bukkit.getPluginManager().getPlugin(name));
	}

	public static Map<String, MonitorRecord> getEventTimingsByPlugin(Plugin plg) {
		Map<String, MonitorRecord> record = new HashMap<String, MonitorRecord>();
		if (plg == null) {
			return record;
		}
		for (RegisteredListener listener : HandlerList.getRegisteredListeners(plg)) {
			try {
				FieldAccessor<EventExecutor> field = Reflection.getField(RegisteredListener.class, "executor", EventExecutor.class);
				EventExecutor executor = field.get(listener);
				if (executor instanceof EventExecutorInjector) {
					EventExecutorInjector eventExecutorInjector = (EventExecutorInjector) executor;
					String eventName = eventExecutorInjector.getEventName();
					if (eventName != null) {
						MonitorRecord monitorRecord = getMonitorRecord(eventName, eventExecutorInjector.getTotalTime(), eventExecutorInjector.getTotalCount(),
								eventExecutorInjector.getMaxExecuteTime());
						if (record.containsKey(eventName)) {
							MonitorRecord otherMonitorRecord = record.get(eventName);
							record.put(eventName, otherMonitorRecord.merge(monitorRecord));
						} else {
							record.put(eventName, monitorRecord);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return record;
	}

	// 获取某个插件所有任务的耗时情况，并且全部合并
	public static MonitorRecord getTaskTimingsByPluginName(String name) {
		return getTaskTimingsByPlugin(Bukkit.getPluginManager().getPlugin(name));
	}

	public static MonitorRecord getTaskTimingsByPlugin(Plugin plg) {
		MonitorRecord monitorRecord = getMonitorRecord("Scheduler", 0L, 0L, 0L);
		if (plg == null) {
			return monitorRecord;
		}
		for (BukkitTask pendingTask : Bukkit.getScheduler().getPendingTasks()) {
			if (pendingTask.isSync() && pendingTask.getOwner().equals(plg)) {
				try {
					FieldAccessor<Runnable> field = Reflection.getField(pendingTask.getClass(), "task", Runnable.class);
					Runnable runnable = field.get(pendingTask);
					if (runnable instanceof SchedulerTaskInjector) {
						SchedulerTaskInjector schedulerTaskInjector = (SchedulerTaskInjector) runnable;
						monitorRecord = monitorRecord
								.merge(getMonitorRecord("Scheduler", schedulerTaskInjector.getTotalTime(), schedulerTaskInjector.getTotalCount(), schedulerTaskInjector.getMaxExecuteTime()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return monitorRecord;
	}

	// 获取某个插件命令的消耗，相同命令合并统计
	public static Map<String, MonitorRecord> getCommandTimingsByPluginName(String name) {
		return getCommandTimingsByPlugin(Bukkit.getPluginManager().getPlugin(name));
	}

	public static Map<String, MonitorRecord> getCommandTimingsByPlugin(Plugin plg) {
		Map<String, MonitorRecord> record = new HashMap<String, MonitorRecord>();
		if (plg == null) {
			return record;
		}
		try {
			SimpleCommandMap simpleCommandMap = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());
			for (Command command : simpleCommandMap.getCommands()) {
				if (command instanceof PluginCommand) {
					PluginCommand pluginCommand = (PluginCommand) command;
					if (plg.equals(pluginCommand.getPlugin())) {
						FieldAccessor<CommandExecutor> commandField = Reflection.getField(PluginCommand.class, "executor", CommandExecutor.class);
						CommandExecutor executor = commandField.get(pluginCommand);
						if (executor instanceof CommandInjector) {
							CommandInjector commandInjector = (CommandInjector) executor;
							record = mergeRecordMap(record, commandInjector.getMonitorRecordMap());
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;
	}

	private static MonitorRecord getMonitorRecord(String name, long totalTime, long totalCount, long maxExecuteTime) {
		MonitorRecord monitorRecord = new MonitorRecord(name);
		monitorRecord.increaseTotalTime(totalTime);
		monitorRecord.setTotalCount(totalCount);
		monitorRecord.setMaxExecuteTime(maxExecuteTime);
		return monitorRecord;
	}

	private static Map<String, MonitorRecord> mergeRecordMap(Map<String, MonitorRecord> record1, Map<String, MonitorRecord> record2) {
		for (Entry<String, MonitorRecord> entry : record2.entrySet()) {
			if (record1.containsKey(entry.getKey())) {
				record1.put(entry.getKey(), record1.get(entry.getKey()).merge(entry.getValue()));
			} else {
				record1.put(entry.getKey(), entry.getValue());
			}
		}
		return record1;
	}

	public static boolean isEnable() {
		return enable;
	}

	public static long getEnableTime() {
		return enable_time;
	}

}
