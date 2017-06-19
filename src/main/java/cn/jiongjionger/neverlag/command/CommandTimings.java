package cn.jiongjionger.neverlag.command;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.monitor.MonitorUtils;
import cn.jiongjionger.neverlag.monitor.inject.EventExecutorInjector;
import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;

public class CommandTimings implements ISubCommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final String PERMNODE = "neverlag.command.timings";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("timings")) {
			if (args.length == 1) {
				return true;
			}
			switch (args[1].toLowerCase()) {
			case "on":
				MonitorUtils.enable();
				sender.sendMessage("enable");
				break;
			case "off":
				MonitorUtils.disable();
				sender.sendMessage("disable");
				break;
			case "event":
				for (RegisteredListener listener : HandlerList.getRegisteredListeners(Bukkit.getPluginManager().getPlugin(args[2]))) {
					try {
						FieldAccessor<EventExecutor> field = Reflection.getField(RegisteredListener.class, "executor", EventExecutor.class);
						EventExecutor executor = field.get(listener);
						if (executor instanceof EventExecutorInjector) {
							EventExecutorInjector eventExecutorInjector = (EventExecutorInjector) executor;
							for (Entry<String, Long> entry : eventExecutorInjector.getTotalTime().entrySet()) {
								sender.sendMessage("event: " + entry.getKey() + " time: " + entry.getValue());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
			}
		}
		return true;
	}

	@Override
	public String getPermNode() {
		return this.PERMNODE;
	}

}
