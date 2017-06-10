package cn.jiongjionger.neverlag.command;

import java.util.concurrent.atomic.AtomicLong;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;

public class CommandBenchmark implements CommandExecutor {

	private final NeverLag plg = NeverLag.getInstance();
	private final ConfigManager cm = ConfigManager.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("benchmark")) {
			sender.sendMessage(cm.getCommandStartBenchmark());
			this.cpuBenchmark(sender);
		}
		return true;
	}

	private void cpuBenchmark(final CommandSender sender) {
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
			public void run() {
				long operationCount = 0L;
				long startTime = System.nanoTime();
				while (System.nanoTime() - startTime < 5000000000L) {
					Math.pow(1024.0D * Math.random(), Math.random() / 1.024D);
					Math.sqrt(Math.random() + Math.random() * (Math.random() * 1024.0D));
					Math.cbrt(Math.random() + Math.random() * (Math.random() * 1024.0D));
					Math.sin(Math.random() * 360 * Math.PI / 180);
					Math.cos(Math.random() * 360 * Math.PI / 180);
					operationCount = operationCount + 1L;
				}
				// 分数即为5秒内平均每毫秒计算的次数
				String score = String.valueOf(Math.ceil(operationCount / 5000));
				sender.sendMessage(cm.getCommandFinishBenchmark().replace("%SCORE%", score));
			}
		});
	}
}
