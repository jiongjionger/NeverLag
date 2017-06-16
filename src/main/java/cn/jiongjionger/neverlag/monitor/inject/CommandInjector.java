package cn.jiongjionger.neverlag.monitor.inject;

import java.util.List;

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
	private long totalCount = 0L;
	private long totalTime = 0L;
	private long maxExecuteTime = 0L;

	public CommandInjector(Plugin plugin, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
		this.plugin = plugin;
		this.commandExecutor = commandExecutor;
		this.tabCompleter = tabCompleter;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		long startTime = System.nanoTime();
		List<String> tabResult = this.tabCompleter.onTabComplete(sender, command, alias, args);
		long endTime = System.nanoTime();
		long useTime = endTime - startTime;
		if (useTime > this.maxExecuteTime) {
			this.maxExecuteTime = useTime;
		}
		this.totalTime = this.totalTime + useTime;
		this.totalCount = this.totalCount + 1L;
		return tabResult;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		long startTime = System.nanoTime();
		boolean commandResult = this.commandExecutor.onCommand(sender, command, label, args);
		long endTime = System.nanoTime();
		long useTime = endTime - startTime;
		if (useTime > this.maxExecuteTime) {
			this.maxExecuteTime = useTime;
		}
		this.totalTime = this.totalTime + useTime;
		this.totalCount = this.totalCount + 1L;
		return commandResult;
	}

	public static void inject(Plugin plg) {
		if (plg != null) {
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
		}
	}

	public static void uninject(Plugin plg) {
		if (plg != null) {
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
		}
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
