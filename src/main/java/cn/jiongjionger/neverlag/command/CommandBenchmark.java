package cn.jiongjionger.neverlag.command;

import java.text.DecimalFormat;
import org.bukkit.command.CommandSender;

public class CommandBenchmark extends AbstractSubCommand {
	public CommandBenchmark() {
		super("benchmark");
	}

	private void cpuBenchmark(final CommandSender sender) {
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
			@Override
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
				String score = String.valueOf(new DecimalFormat("#").format(Math.ceil(operationCount / 5000)));
				sender.sendMessage(cm.getCommandFinishBenchmark().replace("%SCORE%", score));
			}
		});
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		sender.sendMessage(cm.getCommandStartBenchmark());
		this.cpuBenchmark(sender);
	}

	@Override
	public String getUsage() {
		return null;
	}
}
