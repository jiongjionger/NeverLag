package cn.jiongjionger.neverlag.system;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;

public class RedStoneCounter {

	private final static class RedStoneCounterHolder {
		private final static RedStoneCounter rc = new RedStoneCounter();
	}

	public final static RedStoneCounter getInstance() {
		return RedStoneCounterHolder.rc;
	}

	// 保存红石每触发的次数
	private int syncRestoneCount;
	private AtomicInteger asyncRestoneCount = new AtomicInteger(0);

	// 记录一分钟内的红石触发次数
	private final ConcurrentLinkedDeque<Integer> asyncOneMinutesRecord = new ConcurrentLinkedDeque<>();
	private final LinkedList<Integer> syncOneMinutesRecord = new LinkedList<>();

	private NeverLag plg = NeverLag.getInstance();

	private ConfigManager cm = ConfigManager.getInstance();

	public RedStoneCounter() {
		plg.getServer().getScheduler().runTaskTimerAsynchronously(plg, new Runnable() {
			@Override
			public void run() {
				if (cm.isCheckRedstoneOnAsync()) {
					if (asyncOneMinutesRecord.size() >= 60) { // 双重检查锁定
						synchronized (asyncOneMinutesRecord) {
							if (asyncOneMinutesRecord.size() >= 60) {
								asyncOneMinutesRecord.removeFirst();
							}
						}
					}
					asyncOneMinutesRecord.add(asyncRestoneCount.getAndSet(0));
				}
			}
		}, 20L, 20L);
		plg.getServer().getScheduler().runTaskTimer(plg, new Runnable() {
			@Override
			public void run() {
				if (!cm.isCheckRedstoneOnAsync()) {
					if (syncOneMinutesRecord.size() >= 60) {
						syncOneMinutesRecord.removeFirst();
					}
					syncOneMinutesRecord.add(syncRestoneCount);
					syncRestoneCount = 0;
				}
			}
		}, 20L, 20L);
	}

	public int getRedstoneAvgCount(boolean forceSync) {
		if (forceSync) {
			if (syncOneMinutesRecord.isEmpty()) {
				return 0;
			}
			int total = 0;
			for (int count : syncOneMinutesRecord) {
				total = total + count;
			}
			return total / 60;
		} else {
			synchronized (asyncOneMinutesRecord) {
				if (asyncOneMinutesRecord.isEmpty()) {
					return 0;
				}
				int total = 0;
				for (int count : asyncOneMinutesRecord) {
					total = total + count;
				}
				return total / 60;
			}
		}
	}

	public int getRedstoneRealTimeCount(boolean forceSync) {
		if (forceSync) {
			if (syncOneMinutesRecord.isEmpty()) {
				return 0;
			}
			return syncOneMinutesRecord.getLast();
		} else {
			synchronized (asyncOneMinutesRecord) {
				if (asyncOneMinutesRecord.isEmpty()) {
					return 0;
				}
				return asyncOneMinutesRecord.getLast();
			}
		}
	}

	public void updateRedstoneCount(boolean forceSync) {
		if (forceSync) {
			syncRestoneCount = syncRestoneCount + 1;
		} else {
			asyncRestoneCount.incrementAndGet();
		}
	}

}
