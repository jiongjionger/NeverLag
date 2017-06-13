package cn.jiongjionger.neverlag.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class CommandBase implements CommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private HashMap<String, ISubCommandExecutor> subCommandMap = new HashMap<String, ISubCommandExecutor>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1) {
			ISubCommandExecutor subCommandExecutor = subCommandMap.get(args[0].toLowerCase());
			if (subCommandExecutor != null) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (!p.isOp() && !p.hasPermission(subCommandExecutor.getPermNode())) {
						sender.sendMessage(cm.getCommandNoPerm());
						return true;
					}
				}
				subCommandExecutor.onCommand(sender, cmd, label, args);
			}
		}
		return true;
	}

	public void registerSubCommand(String subCommand, ISubCommandExecutor subCommandExecutor) {
		subCommandMap.put(subCommand.toLowerCase(), subCommandExecutor);
	}
}
