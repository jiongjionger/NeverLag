package cn.jiongjionger.neverlag.monitor.inject;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;

public class CommandInjector implements CommandExecutor {

	private final Plugin plugin;
	private final CommandExecutor commandExecutor;
	private Map<String, Long> totalCount = new HashMap<String, Long>();
	private Map<String, Long> totalTime = new HashMap<String, Long>();
	private Map<String, Long> maxExecuteTime = new HashMap<String, Long>();

	public CommandInjector(Plugin plugin, CommandExecutor commandExecutor) {
		this.plugin = plugin;
		this.commandExecutor = commandExecutor;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Bukkit.isPrimaryThread()) {
			long startTime = System.nanoTime();
			boolean commandResult = this.commandExecutor.onCommand(sender, command, label, args);
			long endTime = System.nanoTime();
			long useTime = endTime - startTime;
			String commandName = command.getName();
			this.record("totalCount", commandName, 1L);
			this.record("totalTime", commandName, useTime);
			this.record("maxExecuteTime", commandName, useTime);
			return commandResult;
		} else {
			return this.commandExecutor.onCommand(sender, command, label, args);
		}
	}

	/*
	 * 记录某个map的值 totalCount 总执行次数为自增 totalTime 总执行时间为累加 maxExecuteTime
	 * 最大执行时间为只保存最大值
	 * 
	 * @param mapName map名字
	 * 
	 * @param key 命令名称
	 * 
	 * @param value 执行时间
	 */
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
			if (maxTime == null || value > maxTime.longValue()) {
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
							CommandInjector commandInjector = new CommandInjector(plg, commandField.get(pluginCommand));
							commandField.set(pluginCommand, commandInjector);
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
							CommandExecutor executor = commandField.get(pluginCommand);
							if (executor instanceof CommandInjector) {
								commandField.set(pluginCommand, ((CommandInjector) executor).getCommandExecutor());
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

	public CommandExecutor getCommandExecutor() {
		return this.commandExecutor;
	}

	public Plugin getPlugin() {
		return this.plugin;
	}
}
