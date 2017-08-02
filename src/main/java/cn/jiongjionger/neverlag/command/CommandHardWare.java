package cn.jiongjionger.neverlag.command;

import org.bukkit.command.CommandSender;
import cn.jiongjionger.neverlag.utils.HardWareInfo;

public class CommandHardWare extends AbstractSubCommand {
	private volatile boolean isRunnning = false;

	public CommandHardWare() {
		super("hardware");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (this.isRunnning) {
			sender.sendMessage(cm.getCommandNoFinishFetchHardWareInfo());
		} else {
			this.isRunnning = true;
			sender.sendMessage(cm.getCommandStartFetchHardWareInfo());
			this.showHardWareInfo(sender);
		}
	}

	private void showHardWareInfo(final CommandSender sender) {
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
			@Override
			public void run() {
				try {
					String jvmInfo = cm.getCommandHardWareJVMInfo().replace("%JVMINFO%", HardWareInfo.getJVMInfo());
					String jvmArg = cm.getCommandHardWareJVMArg().replace("%JVMARG%", HardWareInfo.getJVMArg());
					String systemInfo = cm.getCommandHardWareSystemInfo().replace("%SYSTEMINFO%", HardWareInfo.getSystemInfo());
					String cpuInfo = cm.getCommandHardWareCPUInfo().replace("%CPUINFO%", HardWareInfo.getCPUInfo());
					String memoryInfo = cm.getCommandHardWareMemoryInfo().replace("%MEMORYINFO%", HardWareInfo.getMemoryInfo());
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
