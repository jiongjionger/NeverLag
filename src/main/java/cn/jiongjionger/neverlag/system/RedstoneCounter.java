package cn.jiongjionger.neverlag.system;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class RedstoneCounter {

	private static final class RedstoneCounterHolder {
		private static final RedstoneCounter INSTANCE = new RedstoneCounter();
	}

	public static final RedstoneCounter getInstance() {
		return RedstoneCounterHolder.INSTANCE;
	}

	// 保存红石每触发的次数
	private int syncRestoneCount;
	private final AtomicInteger asyncRestoneCount = new AtomicInteger(0);

	// 记录一分钟内的红石触发次数
	private final ConcurrentLinkedDeque<Integer> asyncOneMinutesRecord = new ConcurrentLinkedDeque<>();
	private final ReentrantLock asyncLock = new ReentrantLock();
	private final LinkedList<Integer> syncOneMinutesRecord = new LinkedList<>();

	private final NeverLag plg = NeverLag.getInstance();

	private final ConfigManager cm = ConfigManager.getInstance();

	public RedstoneCounter() {
		plg.getServer().getScheduler().runTaskTimerAsynchronously(plg, new Runnable() {
			@Override
			public void run() {
				if (cm.redstoneAsyncCheck) {
					if (asyncOneMinutesRecord.size() >= 60) { // 双重检查锁定
						asyncLock.lock();
						try {
							if (asyncOneMinutesRecord.size() >= 60) {
								asyncOneMinutesRecord.removeFirst();
							}
						} finally {
							asyncLock.unlock();
						}
					}
					asyncOneMinutesRecord.add(asyncRestoneCount.getAndSet(0));
				}
			}
		}, 20L, 20L);
		plg.getServer().getScheduler().runTaskTimer(plg, new Runnable() {
			@Override
			public void run() {
				if (!cm.redstoneAsyncCheck) {
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
		Deque<Integer> deque = enterThreadSafe(forceSync);
		try {
			if (deque.isEmpty()) {
				return 0;
			}
			int total = 0;
			for (Integer count : deque) {
				total += count;
			}
			return total / 60;
		} finally {
			leaveThreadSafe(forceSync);
		}
	}

	public int getRedstoneRealTimeCount(boolean forceSync) {
		Deque<Integer> deque = enterThreadSafe(forceSync);
		try {
			if (deque.isEmpty()) {
				return 0;
			}
			return deque.getLast();
		} finally {
			leaveThreadSafe(forceSync);
		}
	}

	private Deque<Integer> enterThreadSafe(boolean forceSync) {
		Deque<Integer> deque;
		if (forceSync) {
			deque = syncOneMinutesRecord;
		} else {
			deque = asyncOneMinutesRecord;
			asyncLock.lock();
		}
		return deque;
	}

	private void leaveThreadSafe(boolean forceSync) {
		try {
			if (!forceSync) asyncLock.unlock();
		} catch (IllegalMonitorStateException ex) {
			// ignore
		}
	}

	public void updateRedstoneCount(boolean forceSync) {
		if (forceSync) {
			syncRestoneCount++;
		} else {
			asyncRestoneCount.incrementAndGet();
		}
	}

}
