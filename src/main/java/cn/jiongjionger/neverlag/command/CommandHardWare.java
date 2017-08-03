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
			sender.sendMessage(i18n.tr("running"));
		} else {
			this.isRunnning = true;
			sender.sendMessage(i18n.tr("start"));
			this.showHardWareInfo(sender);
		}
	}

	private void showHardWareInfo(final CommandSender sender) {
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
			@Override
			public void run() {
				try {
					String result = i18n.tr("result")
						.replace("%JVM_INFO%", HardwareInfo.getJVMInfo())
						.replace("%JVM_ARGS%", HardwareInfo.getJVMArg())
						.replace("%SYSTEM_INFO%", HardwareInfo.getSystemInfo())
						.replace("%CPU_INFO%", HardwareInfo.getCPUInfo())
						.replace("%MEMORY_INFO%", HardwareInfo.getMemoryInfo());
					sender.sendMessage(result.split("\n"));
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
