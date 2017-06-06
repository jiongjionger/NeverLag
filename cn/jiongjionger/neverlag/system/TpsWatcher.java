package cn.jiongjionger.neverlag.system;

import java.util.LinkedList;

public class TpsWatcher implements Runnable {

	private transient long lastPoll = System.nanoTime();
	private final LinkedList<Double> history = new LinkedList<>();
	private final long tickInterval = 50;

	public TpsWatcher() {
		history.add(20.0D);
	}

	@Override
	public void run() {
		final long startTime = System.nanoTime();
		long timeSpent = (startTime - lastPoll) / 1000;
		if (timeSpent == 0) {
			timeSpent = 1;
		}
		if (history.size() > 10) {
			history.removeFirst();
		}
		double tps = tickInterval * 1000000.0 / timeSpent;
		if (tps <= 21) {
			history.add(tps);
		}
		lastPoll = startTime;
	}

	public double getAverageTPS() {
		double avg = 0.0D;
		int size = 0;
		for (Double tps : this.history) {
			if (tps != null) {
				avg += tps;
			}
			size = size + 1;
		}
		return avg / size;
	}

	public double getLastTPS() {
		return history.getLast();
	}
}
