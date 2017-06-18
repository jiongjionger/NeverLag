package cn.jiongjionger.neverlag.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

public class CommandTabComplete implements TabCompleter {

	private static final String[] COMMANDS = { "benchmark", "hardware", "gc", "info", "ping", "chunkinfo", "clear" };
	private static final List<String> CLEARTYPE = new ArrayList<String>();
	private static final String[] CHUNKINFO_TYPE = { "entity", "tiles", "monsters", "animals", "dropitem", "player", "villager", "squid", "chest", "hopper",
			"furnace", "dropper", "dispenser", "piston", "noteblock", "jukebox", "brewing", "cauldron", "armorstand", "skull" };
	private static final String[] TIMINGS = { "on", "off", "lag", "event", "command", "task", "plugin" };
	private static final Set<String> SHOULD_COMPLETE_PLUGIN_NAME = new HashSet<String>();

	static {
		for (EntityType type : EntityType.values()) {
			@SuppressWarnings("deprecation")
			String typeName = type.getName();
			if (typeName != null && !"player".equals(typeName.toLowerCase())) {
				CLEARTYPE.add(typeName);
			}
		}
		CLEARTYPE.add("dropitem");
		CLEARTYPE.add("animals");
		CLEARTYPE.add("monsters");
		SHOULD_COMPLETE_PLUGIN_NAME.add("event");
		SHOULD_COMPLETE_PLUGIN_NAME.add("command");
		SHOULD_COMPLETE_PLUGIN_NAME.add("task");
		SHOULD_COMPLETE_PLUGIN_NAME.add("plugin");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args == null || command == null) {
			return null;
		}
		// 返回的结果
		List<String> completions = new ArrayList<String>();
		switch (args.length) {
		case 1:
			StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
			break;
		case 2:
			if (args[0].equalsIgnoreCase("clear")) {
				StringUtil.copyPartialMatches(args[1], CLEARTYPE, completions);
				break;
			}
			if (args[0].equalsIgnoreCase("chunkinfo")) {
				StringUtil.copyPartialMatches(args[1], Arrays.asList(CHUNKINFO_TYPE), completions);
				break;
			}
			if (args[0].equalsIgnoreCase("timings")) {
				StringUtil.copyPartialMatches(args[1], Arrays.asList(TIMINGS), completions);
				break;
			}
		case 3:
			if (args[0].equalsIgnoreCase("timings") && SHOULD_COMPLETE_PLUGIN_NAME.contains(args[1].toLowerCase())) {
				StringUtil.copyPartialMatches(args[2], getAllPluginName(), completions);
				break;
			}
		default:
			break;
		}

		Collections.sort(completions);
		return completions;
	}

	private List<String> getAllPluginName() {
		List<String> pluginNameList = new ArrayList<String>();
		for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if (plugin != null && plugin.isEnabled()) {
				pluginNameList.add(plugin.getName());
			}
		}
		return pluginNameList;
	}
}
