package cn.jiongjionger.neverlag.monitor;

import java.util.Objects;

public class PluginRecord {

	private String name;
	private long totalTime;
	private long totalCount;

	public PluginRecord(String pluginName) {
		this.name = pluginName;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public String getName() {
		return name;
	}

	public long getAvgProportion() {
		if (this.totalCount == 0) {
			return 0;
		}
		return this.totalTime / this.totalCount / 10000L / 50;
	}

	@Override
	public String toString() {
		return String.format("%s:[count: %s, total: %s]", getName(), getTotalCount(), getTotalTime());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		return Objects.equals(this.name, ((PluginRecord) obj).name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name) + 1;
	}
}
