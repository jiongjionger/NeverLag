package cn.jiongjionger.neverlag.monitor.inject;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.TimedRegisteredListener;

import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;

public class EventExecutorInjector implements EventExecutor {

	private final Plugin plugin;
	private final EventExecutor eventExecutor;
	private Map<String, Long> totalCount = new HashMap<String, Long>();
	private Map<String, Long> totalTime = new HashMap<String, Long>();
	private Map<String, Long> maxExecuteTime = new HashMap<String, Long>();

	public EventExecutorInjector(Plugin plugin, EventExecutor eventExecutor) {
		this.plugin = plugin;
		this.eventExecutor = eventExecutor;
	}

	@Override
	// 计算调用次数和花费总时间以及花费最多的时间
	public void execute(Listener listener, Event e) throws EventException {
		if (e.isAsynchronous()) {
			this.eventExecutor.execute(listener, e);
		} else {
			long startTime = System.nanoTime();
			this.eventExecutor.execute(listener, e);
			long endTime = System.nanoTime();
			long executeTime = endTime - startTime;
			String eventName = e.getEventName();
			this.record("totalCount", eventName, 1L);
			this.record("totalTime", eventName, executeTime);
			this.record("maxExecuteTime", eventName, executeTime);
		}
	}

	private void record(String mapName, String key, long value) {
		switch (mapName) {
		case "totalCount":
			Long count = this.totalCount.get(key);
			if (count == null) {
				this.totalCount.put(key, 1L);
			} else {
				this.totalCount.put(key, count + 1L);
			}
			break;
		case "totalTime":
			Long time = this.totalTime.get(key);
			if (time == null) {
				this.totalTime.put(key, value);
			} else {
				this.totalTime.put(key, time + value);
			}
			break;
		case "maxExecuteTime":
			Long maxTime = this.maxExecuteTime.get(key);
			if (maxTime == null || value > maxTime.longValue()) {
				this.maxExecuteTime.put(key, value);
			}
			break;
		default:
			break;
		}
	}

	// 将监听器原本的EventExecutor替换成带性能统计的版本
	public static void inject(Plugin plg) {
		if (plg != null) {
			for (RegisteredListener listener : HandlerList.getRegisteredListeners(plg)) {
				try {
					if (!(listener instanceof TimedRegisteredListener)) {
						HandlerList.unregisterAll(listener.getListener());
						FieldAccessor<EventExecutor> field = Reflection.getField(RegisteredListener.class, "executor", EventExecutor.class);
						EventExecutor fieldEventExecutor = field.get(listener);
						field.set(listener, new EventExecutorInjector(plg, fieldEventExecutor));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 将监听器带性能统计的版本替换回原始的EventExecutor版本
	public static void uninject(Plugin plg) {
		if (plg != null) {
			for (RegisteredListener listener : HandlerList.getRegisteredListeners(plg)) {
				try {
					if (!(listener instanceof TimedRegisteredListener)) {
						FieldAccessor<EventExecutor> field = Reflection.getField(RegisteredListener.class, "executor", EventExecutor.class);
						EventExecutor executor = field.get(listener);
						if (executor instanceof EventExecutorInjector) {
							HandlerList.unregisterAll(listener.getListener());
							field.set(listener, ((EventExecutorInjector) executor).getEventExecutor());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Map<String, Long> getTotalCount() {
		return this.totalCount;
	}

	public Map<String, Long> getTotalTime() {
		return this.totalTime;
	}

	public Map<String, Long> getMaxExecuteTime() {
		return this.maxExecuteTime;
	}

	// 获取原本的EventExecutor
	public EventExecutor getEventExecutor() {
		return this.eventExecutor;
	}

	public Plugin getPlugin() {
		return this.plugin;
	}

}
