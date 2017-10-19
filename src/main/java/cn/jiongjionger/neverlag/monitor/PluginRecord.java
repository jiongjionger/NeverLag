package cn.jiongjionger.neverlag.monitor;

import java.util.Objects;

public class PluginRecord {

	private final String name;
	private long totalTime;
	private long totalCount;

	public PluginRecord(String pluginName) {
		this.name = pluginName;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj != null &&
			getClass() == obj.getClass() &&
			Objects.equals(this.name, ((PluginRecord) obj).name);
	}

	public long getAvgProportion() {
		if (this.totalCount == 0) {
			return 0;
		}
		return this.totalTime / this.totalCount / 10000L / 50;
	}

	public String getName() {
		return name;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public long getTotalTime() {
		return this.totalTime;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name) + 1;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public String toString() {
		return String.format("%s:[count: %s, total: %s]", getName(), getTotalCount(), getTotalTime());
	}
}
