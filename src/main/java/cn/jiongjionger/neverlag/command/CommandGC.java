package cn.jiongjionger.neverlag.command;

import org.bukkit.command.CommandSender;

public class CommandGC extends AbstractSubCommand {

	public CommandGC() {
		super("gc");
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		long memoryBeforeGC = getCurrentMemoryUsage();
		System.runFinalization();
		System.gc();
		long memoryAfterGC = getCurrentMemoryUsage();
		if (memoryAfterGC < memoryBeforeGC) {
			long gcMemory = (memoryAfterGC - memoryBeforeGC) / 1024 / 1024;
			sender.sendMessage(cm.getCommandGCMessage().replace("%GCMEMORY%", String.valueOf(gcMemory)));
		} else {
			sender.sendMessage(cm.getCommandGCNoEffectMessage());
		}
	}
	
	protected long getCurrentMemoryUsage() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	@Override
	public String getUsage() {
		return null;
	}
}
