package cn.jiongjionger.neverlag.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandBase implements CommandExecutor {

	private HashMap<String, CommandExecutor> subCommandMap = new HashMap<String, CommandExecutor>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1) {
			CommandExecutor subCommandExecutor = subCommandMap.get(args[0].toLowerCase());
			if (subCommandExecutor != null) {
				subCommandExecutor.onCommand(sender, cmd, label, args);
			}
		}
		return true;
	}

	public void registerSubCommand(String subCommand, CommandExecutor subCommandExecutor) {
		subCommandMap.put(subCommand.toLowerCase(), subCommandExecutor);
	}
}
