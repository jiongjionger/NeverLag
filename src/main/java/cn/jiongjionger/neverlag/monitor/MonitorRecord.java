package cn.jiongjionger.neverlag.monitor;

import java.util.Objects;

public class MonitorRecord {

	private final String name;
	private long totalCount = 0L;
	private long totalTime = 0L;
	private long maxExecuteTime = 0L;

	public MonitorRecord(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj != null &&
			getClass() == obj.getClass() &&
			Objects.equals(this.name, ((MonitorRecord) obj).name);
	}

	public long getAvgExecuteTime() {
		if (totalCount == 0) {
			return 0;
		}
		return totalTime / totalCount;
	}

	public long getMaxExecuteTime() {
		return maxExecuteTime;
	}

	public String getName() {
		return name;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public long getTotalTime() {
		return totalTime;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name) + 1;
	}

	public long increaseTotalCount() {
		return ++totalCount;
	}

	public long increaseTotalTime(long time) {
		totalTime += time;
		return totalTime;
	}

	public MonitorRecord merge(MonitorRecord monitorRecord) {
		MonitorRecord newMonitorRecord = new MonitorRecord(this.name);
		newMonitorRecord.setTotalCount(monitorRecord.getTotalCount() + this.totalCount);
		newMonitorRecord.increaseTotalTime(monitorRecord.getTotalTime() + this.totalTime);
		newMonitorRecord.setMaxExecuteTime(Math.max(this.maxExecuteTime, monitorRecord.getMaxExecuteTime()));
		return newMonitorRecord;
	}

	public void setMaxExecuteTime(long time) {
		this.maxExecuteTime = time;
	}

	public void setTotalCount(long count) {
		this.totalCount = count;
	}

	@Override
	public String toString() {
		return String.format("%s:[count: %s, total: %s, avg: %s, max: %s]", getName(), getTotalCount(), getTotalTime(), getAvgExecuteTime(), getMaxExecuteTime());
	}

	public void update(long time) {
		increaseTotalCount();
		increaseTotalTime(time);
		updateMaxExecuteTime(time);
	}

	public long updateMaxExecuteTime(long time) {
		return this.maxExecuteTime = Math.max(this.maxExecuteTime, time);
	}
}
