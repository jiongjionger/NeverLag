package cn.jiongjionger.neverlag.command;

import cn.jiongjionger.neverlag.utils.HardwareInfo;
import org.bukkit.command.CommandSender;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandHardWare extends AbstractSubCommand {
	private volatile Future<?> future = null;
	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>());

	public CommandHardWare() {
		super("hardware");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (future != null && !future.isDone()) {
			sender.sendMessage(i18n.tr("running"));
		} else {
			sender.sendMessage(i18n.tr("start"));
			this.showHardWareInfo(sender);
		}
	}

	private void showHardWareInfo(final CommandSender sender) {
		future = executor.submit(new Runnable() {
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
				}
			}
		});
	}
}
