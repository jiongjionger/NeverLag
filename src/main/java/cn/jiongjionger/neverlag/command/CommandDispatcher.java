package cn.jiongjionger.neverlag.command;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.util.StringUtil;

public class CommandDispatcher implements CommandExecutor, TabCompleter {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final Map<String, AbstractSubCommand> subCommandMap = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag")) {
			if(args.length == 0) {
				// TODO 显示帮助信息
				throw new UnsupportedOperationException();
			}
			
			AbstractSubCommand executor = subCommandMap.get(args[0].toLowerCase());
			if(executor == null) {
				// TODO 提示命令不存在
				throw new UnsupportedOperationException();
			}
			if (!sender.hasPermission(executor.getPermission())) {
				sender.sendMessage(cm.commandNoPerm);
				return true;
			}
			if(executor.isPlayerRequired() && !(sender instanceof Player)) {  // 如果命令要求玩家才能执行, 而发送者又不是玩家
				// TODO 提示命令只能由玩家执行
				throw new UnsupportedOperationException();
			}
			String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
			if(executor.getMinimumArgCount() > 0 && subCommandArgs.length < executor.getMinimumArgCount()) {
				sender.sendMessage(executor.getUsage());
				return true;
			}
			
			try {
				executor.onCommand(sender, subCommandArgs);
			} catch (MissingCommandArgumentException ex) {
				if(ex.getLocalizedMessage() == null) {
					sender.sendMessage(executor.getUsage());
				} else {
					sender.sendMessage(ex.getLocalizedMessage());
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list;
		if(args.length <= 1) {
			list = new ArrayList<>(subCommandMap.keySet());
		} else {
			AbstractSubCommand subCommand = subCommandMap.get(args[0].toLowerCase());
			if(subCommand == null) {
				return null;
			}
			list = subCommand.onTabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
		}
		
		if(list == null || list.isEmpty()) {
			return null;
		}
		List<String> result = new ArrayList<>();
		StringUtil.copyPartialMatches(args[args.length - 1], list, result);
		return result;
	}

	public void registerSubCommand(AbstractSubCommand subCommandExecutor) {
		subCommandMap.put(subCommandExecutor.getName(), subCommandExecutor);
	}
}
