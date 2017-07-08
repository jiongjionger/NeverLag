package cn.jiongjionger.neverlag.command;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.monitor.MonitorRecord;
import cn.jiongjionger.neverlag.monitor.MonitorUtils;

public class CommandTimings implements ISubCommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final String PERMNODE = "neverlag.command.timings";

	// 纯debug状态

	@Override
	public String getPermNode() {
		return this.PERMNODE;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("timings")) {
			switch (args[1].toLowerCase()) {
			case "on":
				MonitorUtils.enable();
				break;
			case "off":
				MonitorUtils.disable();
				break;
			case "event":
				Map<String, MonitorRecord> map = MonitorUtils.getEventTimingsByPluginName(args[2]);
				for (Entry<String, MonitorRecord> entry : map.entrySet()) {
					sender.sendMessage("eventName: " + entry.getKey() + " ," + entry.getValue().toString());
				}
			case "task":
				sender.sendMessage(MonitorUtils.getTaskTimingsByPluginName(args[2]).toString());
				break;
			case "command":
				Map<String, MonitorRecord> commandRecordMap = MonitorUtils.getCommandTimingsByPluginName(args[2]);
				for (Entry<String, MonitorRecord> entry : commandRecordMap.entrySet()) {
					sender.sendMessage("command: " + entry.getKey() + " ," + entry.getValue().toString());
				}
				break;
			default:
				break;
			}
		}
		return true;
	}

}
