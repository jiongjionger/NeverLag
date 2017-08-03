package cn.jiongjionger.neverlag.command;

import org.bukkit.command.CommandSender;
import cn.jiongjionger.neverlag.utils.HardwareInfo;

public class CommandHardWare extends AbstractSubCommand {
	private volatile boolean isRunnning = false;

	public CommandHardWare() {
		super("hardware");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (this.isRunnning) {
			sender.sendMessage(cm.commandNoFinishFetchHardWareInfo);
		} else {
			this.isRunnning = true;
			sender.sendMessage(cm.commandStartFetchHardWareInfo);
			this.showHardWareInfo(sender);
		}
	}

	private void showHardWareInfo(final CommandSender sender) {
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
			@Override
			public void run() {
				try {
					String jvmInfo = cm.commandHardWareJVMInfo.replace("%JVMINFO%", HardwareInfo.getJVMInfo());
					String jvmArg = cm.commandHardWareJVMArg.replace("%JVMARG%", HardwareInfo.getJVMArg());
					String systemInfo = cm.commandHardWareSystemInfo.replace("%SYSTEMINFO%", HardwareInfo.getSystemInfo());
					String cpuInfo = cm.commandHardWareCPUInfo.replace("%CPUINFO%", HardwareInfo.getCPUInfo());
					String memoryInfo = cm.commandHardWareMemoryInfo.replace("%MEMORYINFO%", HardwareInfo.getMemoryInfo());
					sender.sendMessage(systemInfo);
					sender.sendMessage(jvmInfo);
					sender.sendMessage(jvmArg);
					sender.sendMessage(cpuInfo);
					sender.sendMessage(memoryInfo);
				} catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage(e.toString());
				} finally {
					isRunnning = false;
				}
			}
		});
	}

	@Override
	public String getUsage() {
		return null;
	}
}
