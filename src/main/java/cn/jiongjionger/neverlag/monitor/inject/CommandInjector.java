package cn.jiongjionger.neverlag.monitor.inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;

public class CommandInjector implements TabExecutor {
	private final Plugin plugin;
	private final CommandExecutor commandExecutor;
	private final TabCompleter tabCompleter;
	private final Map<String, Long> totalCount = new HashMap<String, Long>();
	private final Map<String, Long> totalTime = new HashMap<String, Long>();
	private final Map<String, Long> maxExecuteTime = new HashMap<String, Long>();

	public CommandInjector(Plugin plugin, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
		this.plugin = plugin;
		this.commandExecutor = commandExecutor;
		this.tabCompleter = tabCompleter;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return this.tabCompleter.onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Bukkit.isPrimaryThread()) {
			long startTime = System.nanoTime();
			boolean commandResult;
			try{
				commandResult = this.commandExecutor.onCommand(sender, command, label, args);
			}finally{
				long endTime = System.nanoTime();
				long useTime = endTime - startTime;
				String commandName = command.getName();
				this.record("totalCount", commandName, 1L);
				this.record("totalTime", commandName, useTime);
				this.record("maxExecuteTime", commandName, useTime);
			}
			return commandResult;
		} else {
			return this.commandExecutor.onCommand(sender, command, label, args);
		}
	}

	private void record(String mapName, String key, long value) {
		switch (mapName) {
		case "totalCount":
			Long count = this.totalCount.get(key);
			if (count == null) {
				this.totalCount.put(key, 1L);
			} else {
				this.totalCount.put(key, count + 1L);
			}
			break;
		case "totalTime":
			Long time = this.totalTime.get(key);
			if (time == null) {
				this.totalTime.put(key, value);
			} else {
				this.totalTime.put(key, time + value);
			}
			break;
		case "maxExecuteTime":
			Long maxTime = this.maxExecuteTime.get(key);
			if (maxTime == null || value > maxTime) {
				this.maxExecuteTime.put(key, value);
			}
			break;
		default:
			break;
		}
	}

	public static void inject(Plugin plg) {
		if (plg != null) {
			try {
				SimpleCommandMap simpleCommandMap = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());
				for (Command command : simpleCommandMap.getCommands()) {
					if (command instanceof PluginCommand) {
						PluginCommand pluginCommand = (PluginCommand) command;
						if (plg.equals(pluginCommand.getPlugin())) {
							FieldAccessor<CommandExecutor> commandField = Reflection.getField(PluginCommand.class, "executor", CommandExecutor.class);
							FieldAccessor<TabCompleter> tabField = Reflection.getField(PluginCommand.class, "completer", TabCompleter.class);
							CommandInjector commandInjector = new CommandInjector(plg, commandField.get(pluginCommand), tabField.get(pluginCommand));
							commandField.set(pluginCommand, commandInjector);
							tabField.set(pluginCommand, commandInjector);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void uninject(Plugin plg) {
		if (plg != null) {
			try {
				SimpleCommandMap simpleCommandMap = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());
				for (Command command : simpleCommandMap.getCommands()) {
					if (command instanceof PluginCommand) {
						PluginCommand pluginCommand = (PluginCommand) command;
						if (plg.equals(pluginCommand.getPlugin())) {
							FieldAccessor<CommandExecutor> commandField = Reflection.getField(PluginCommand.class, "executor", CommandExecutor.class);
							FieldAccessor<TabCompleter> tabField = Reflection.getField(PluginCommand.class, "completer", TabCompleter.class);
							CommandExecutor executor = commandField.get(pluginCommand);
							if (executor instanceof CommandInjector) {
								commandField.set(pluginCommand, ((CommandInjector) executor).getCommandExecutor());
							}
							TabCompleter completer = tabField.get(pluginCommand);
							if (completer instanceof CommandInjector) {
								tabField.set(pluginCommand, ((CommandInjector) executor).getTabCompleter());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, Long> getTotalCount() {
		return this.totalCount;
	}

	public Map<String, Long> getTotalTime() {
		return this.totalTime;
	}

	public Map<String, Long> getMaxExecuteTime() {
		return this.maxExecuteTime;
	}

	public TabCompleter getTabCompleter() {
		return this.tabCompleter;
	}

	public CommandExecutor getCommandExecutor() {
		return this.commandExecutor;
	}

	public Plugin getPlugin() {
		return this.plugin;
	}
}
