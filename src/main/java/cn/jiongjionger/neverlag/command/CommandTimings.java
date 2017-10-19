package cn.jiongjionger.neverlag.command;

import cn.jiongjionger.neverlag.monitor.MonitorRecord;
import cn.jiongjionger.neverlag.monitor.MonitorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CommandTimings extends AbstractSubCommand {
	private static final List<String> COMPLETION_LIST = Arrays.asList("on", "off", "event", "task", "command");
	private List<String> pluginNames;

	public CommandTimings() {
		super("timings", 1);
	}

	// 纯debug状态

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		switch (args[0].toLowerCase()) {
		case "on":
			MonitorUtils.enable();
			break;
		case "off":
			MonitorUtils.disable();
			break;
		case "event":
			if (args.length < 2) {
				throw MissingCommandArgumentException.INSTANCE;
			}
			Map<String, MonitorRecord> map = MonitorUtils.getEventTimingsByPluginName(args[1]);
			for (Entry<String, MonitorRecord> entry : map.entrySet()) {
				sender.sendMessage("eventName: " + entry.getKey() + " ," + entry.getValue().toString());
			}
		case "task":
			if (args.length < 2) {
				throw MissingCommandArgumentException.INSTANCE;
			}
			sender.sendMessage(MonitorUtils.getTaskTimingsByPluginName(args[1]).toString());
			break;
		case "command":
			if (args.length < 2) {
				throw MissingCommandArgumentException.INSTANCE;
			}
			Map<String, MonitorRecord> commandRecordMap = MonitorUtils.getCommandTimingsByPluginName(args[1]);
			for (Entry<String, MonitorRecord> entry : commandRecordMap.entrySet()) {
				sender.sendMessage("command: " + entry.getKey() + " ," + entry.getValue().toString());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		if (args.length <= 1) {
			return COMPLETION_LIST;
		}

		// 延迟加载, 因为此类构造方法被调用时, 插件可能还没全部 enable
		if (pluginNames == null) {
			pluginNames = new ArrayList<>();
			for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
				if (plugin.isEnabled()) {
					pluginNames.add(plugin.getName());
				}
			}
		}

		return pluginNames;
	}
}
