package cn.jiongjionger.neverlag.monitor.inject;

import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.util.List;

public class CommandInjector extends AbstractMultipleInjector implements TabExecutor {

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

	private final CommandExecutor commandExecutor;
	private final TabCompleter tabCompleter;

	public CommandInjector(Plugin plugin, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
		super(plugin);
		this.commandExecutor = commandExecutor;
		this.tabCompleter = tabCompleter;
	}

	public CommandExecutor getCommandExecutor() {
		return this.commandExecutor;
	}

	public TabCompleter getTabCompleter() {
		return this.tabCompleter;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Bukkit.isPrimaryThread()) {
			long startTime = System.nanoTime();
			try {
				return this.commandExecutor.onCommand(sender, command, label, args);
			} finally {
				long endTime = System.nanoTime();
				long useTime = endTime - startTime;
				this.record(command.getName(), useTime);
			}
		} else {
			return this.commandExecutor.onCommand(sender, command, label, args);
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (tabCompleter == null)
			return null; // onTabComplete 返回 null 表示使用默认 completer
		return this.tabCompleter.onTabComplete(sender, command, alias, args);
	}
}
