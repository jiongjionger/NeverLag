package cn.jiongjionger.neverlag.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.monitor.MonitorUtils;

public class CommandTimings implements ISubCommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final String PERMNODE = "neverlag.command.timings";
	
	// 纯debug状态
	
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
				//MonitorUtils.getAvgExecuteTime(Bukkit.getPluginManager().getPlugin(args[2]));
			default:
				break;
			}
		}
		return true;
	}
	
	@Override
	public String getPermNode(){
		return this.PERMNODE;
	}

}
