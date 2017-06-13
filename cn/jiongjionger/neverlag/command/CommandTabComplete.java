package cn.jiongjionger.neverlag.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class CommandTabComplete implements TabCompleter {

	private static final String[] COMMANDS = { "benchmark", "hardware", "gc", "info", "ping", "chunkinfo", "clear" };
	private static final String[] CLEARTYPE = { "dropitem", "animals", "monsters", "entity" };

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args == null || command == null) {
			return null;
		}
		// 返回的结果
		List<String> completions = new ArrayList<String>();
		switch (args.length) {
		case 1:
			List<String> commands = new ArrayList<String>(Arrays.asList(COMMANDS));
			StringUtil.copyPartialMatches(args[0], commands, completions);
			break;
		case 2:
			if (args[0].equalsIgnoreCase("clear")) {
				List<String> cleartype = new ArrayList<String>(Arrays.asList(CLEARTYPE));
				StringUtil.copyPartialMatches(args[1], cleartype, completions);
				break;
			}
		}
		Collections.sort(completions);
		return completions;
	}
}
