package cn.jiongjionger.neverlag.monitor.inject;

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

public class EventExecutorInjector extends AbstractMultipleInjector implements EventExecutor {
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
	
	private final EventExecutor eventExecutor;

	public EventExecutorInjector(Plugin plugin, EventExecutor eventExecutor) {
		super(plugin);
		this.eventExecutor = eventExecutor;
	}

	@Override
	// 计算调用次数和花费总时间以及花费最多的时间
	public void execute(Listener listener, Event e) throws EventException {
		if (e.isAsynchronous()) {
			this.eventExecutor.execute(listener, e);
		} else {
			long startTime = System.nanoTime();
			try{
				this.eventExecutor.execute(listener, e);
			}finally{
				long endTime = System.nanoTime();
				long executeTime = endTime - startTime;
				this.record(e.getEventName(), executeTime);
			}
		}
	}

	// 获取原本的EventExecutor
	public EventExecutor getEventExecutor() {
		return this.eventExecutor;
	}
}
