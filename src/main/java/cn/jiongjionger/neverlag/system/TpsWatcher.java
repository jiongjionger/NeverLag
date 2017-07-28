package cn.jiongjionger.neverlag.system;

import java.util.LinkedList;

public class TpsWatcher implements Runnable {

	protected long lastPoll = System.currentTimeMillis();
	private final LinkedList<Double> history = new LinkedList<>();

	public TpsWatcher() {
		history.add(20.0D);
	}

	// 求上个 10 秒内服务器的平均TPS
	public double getAverageTPS() {
		double avg = 0.0D;
		for (Double tps : this.history) {
			if (tps != null) {
				avg += tps;
			}
		}
		return avg / history.size();
	}

	public double getTps() {
		return history.getLast();
	}
	
	protected double calcuateTPS(long interval) {
		return 1D / (interval / 1000D);
	}
	
	// 测试用
	protected long currentTime() {
		return System.currentTimeMillis();
	}

	@Override
	public void run() {
		final long startTime = currentTime();
		try {
			long timeSpent = startTime - lastPoll;
			if (timeSpent <= 0) {
				return;
			}
			if (history.size() > 50 * 10) {  // 历史记录保留10秒
				history.poll();
			}
			double tps = calcuateTPS(timeSpent);
			if (tps < 21) {
				history.add(tps);
			}
		} finally {
			lastPoll = startTime;
		}
	}
}
