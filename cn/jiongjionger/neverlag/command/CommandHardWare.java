package cn.jiongjionger.neverlag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.HardWareInfo;

public class CommandHardWare implements CommandExecutor {

	private final NeverLag plg = NeverLag.getInstance();
	private final ConfigManager cm = ConfigManager.getInstance();
	private boolean isRun = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("hardware")) {
			if (this.isRun) {
				sender.sendMessage(cm.getCommandNoFinishFetchHardWareInfo());
			} else {
				this.isRun = true;
				sender.sendMessage(cm.getCommandStartFetchHardWareInfo());
				this.showHardWareInfo(sender);
			}
		}
		return true;
	}

	private void showHardWareInfo(final CommandSender sender) {
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
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
					isRun = false;
				} catch (Exception e) {
					isRun = false;
				}
			}
		});
	}
}
