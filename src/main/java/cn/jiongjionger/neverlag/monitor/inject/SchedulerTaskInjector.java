package cn.jiongjionger.neverlag.monitor.inject;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;

public class SchedulerTaskInjector implements Runnable {

	private final Plugin plugin;
	private final Runnable runnable;
	private long totalCount = 0L;
	private long totalTime = 0L;
	private long maxExecuteTime = 0L;

	public SchedulerTaskInjector(Plugin plugin, Runnable runnable) {
		this.plugin = plugin;
		this.runnable = runnable;
	}

	// 统计定时任务的耗时、次数、最大耗时
	@Override
	public void run() {
		long startTime = System.nanoTime();
		try{
			this.runnable.run();
		}finally{
			long endTime = System.nanoTime();
			long useTime = endTime - startTime;
			if (useTime > this.maxExecuteTime) {
				this.maxExecuteTime = useTime;
			}
			this.totalTime = this.totalTime + useTime;
			this.totalCount = this.totalCount + 1L;
		}
	}

	// 替换原本的Runnable为带性能统计的版本
	public static void inject(Plugin plg) {
		if (plg != null) {
			for (BukkitTask pendingTask : Bukkit.getScheduler().getPendingTasks()) {
				if (pendingTask.isSync() && pendingTask.getOwner().equals(plg)) {
					try {
						FieldAccessor<Runnable> field = Reflection.getField(pendingTask.getClass(), "task", Runnable.class);
						field.set(pendingTask, new SchedulerTaskInjector(plg, field.get(pendingTask)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 替换带性能统计版本的Runnable为原版本
	public static void uninject(Plugin plg) {
		if (plg != null) {
			for (BukkitTask pendingTask : Bukkit.getScheduler().getPendingTasks()) {
				if (pendingTask.isSync() && pendingTask.getOwner().equals(plg)) {
					try {
						FieldAccessor<Runnable> field = Reflection.getField(pendingTask.getClass(), "task", Runnable.class);
						Runnable runnable = field.get(pendingTask);
						if (runnable instanceof SchedulerTaskInjector) {
							field.set(pendingTask, ((SchedulerTaskInjector) runnable).getRunnable());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public long getTotalTime() {
		return this.totalTime;
	}

	public long getMaxExecuteTime() {
		return this.maxExecuteTime;
	}

	public Runnable getRunnable() {
		return this.runnable;
	}

	public Plugin getPlugin() {
		return this.plugin;
	}
}
