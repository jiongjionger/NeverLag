package cn.jiongjionger.neverlag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class CommandGC implements CommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("gc")) {
			long memoryBeforeGC = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			System.runFinalization();
			System.gc();
			long memoryAfterGC = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			if (memoryAfterGC < memoryBeforeGC) {
				long gcMemory = (memoryAfterGC - memoryBeforeGC) / 1024 / 1024;
				sender.sendMessage(cm.getCommandGCMessage().replace("%GCMEMORY%", String.valueOf(gcMemory)));
			} else {
				sender.sendMessage(cm.getCommandGCNoEffectMessage());
			}
		}
		return true;
	}
}
