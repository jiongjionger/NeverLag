package cn.jiongjionger.neverlag.monitor.inject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import cn.jiongjionger.neverlag.monitor.MonitorRecord;

public abstract class AbstractMultipleInjector extends AbstractInjector implements IInjector {

	protected final Map<String, MonitorRecord> map = new HashMap<>();

	public AbstractMultipleInjector(Plugin plugin) {
		super(plugin);
	}

	public Map<String, Long> getAvgExecuteTime() {
		return Maps.transformValues(map, new Function<MonitorRecord, Long>() {
			@Override
			public Long apply(MonitorRecord input) {
				return input.getAvgExecuteTime();
			}
		});
	}

	public Map<String, Long> getMaxExecuteTime() {
		return Maps.transformValues(map, new Function<MonitorRecord, Long>() {
			@Override
			public Long apply(MonitorRecord input) {
				return input.getMaxExecuteTime();
			}
		});
	}

	public Map<String, MonitorRecord> getMonitorRecordMap() {
		return this.map;
	}

	public Map<String, MonitorRecord> getRecords() {
		return Collections.unmodifiableMap(map);
	}

	public Map<String, Long> getTotalCount() {
		return Maps.transformValues(map, new Function<MonitorRecord, Long>() {
			@Override
			public Long apply(MonitorRecord input) {
				return input.getTotalCount();
			}
		});
	}

	public Map<String, Long> getTotalTime() {
		return Maps.transformValues(map, new Function<MonitorRecord, Long>() {
			@Override
			public Long apply(MonitorRecord input) {
				return input.getTotalTime();
			}
		});
	}

	protected void record(String key, long time) {
		MonitorRecord record = this.map.get(key);
		if (record == null)
			this.map.put(key, record = new MonitorRecord(key));
		record.update(time);
	}
}
