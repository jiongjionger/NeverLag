package cn.jiongjionger.neverlag.command;

import java.text.DecimalFormat;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.bukkit.command.CommandSender;

public class CommandBenchmark extends AbstractSubCommand {
	private volatile Future<?> future = null;
	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>());
	
	public CommandBenchmark() {
		super("benchmark");
	}

	private void cpuBenchmark(final CommandSender sender) {
		future = executor.submit(new Runnable() {
			@Override
			public void run() {
				long operationCount = 0L;
				long startTime = System.nanoTime();
				while (System.nanoTime() - startTime < TimeUnit.SECONDS.toNanos(5)) {
					Math.pow(1024.0D * Math.random(), Math.random() / 1.024D);
					Math.sqrt(Math.random() + Math.random() * (Math.random() * 1024.0D));
					Math.cbrt(Math.random() + Math.random() * (Math.random() * 1024.0D));
					Math.sin(Math.random() * 360 * Math.PI / 180);
					Math.cos(Math.random() * 360 * Math.PI / 180);
					operationCount++;
				}
				// 分数即为5秒内平均每毫秒计算的次数
				String score = String.valueOf(new DecimalFormat("#").format(Math.ceil(operationCount / 5000)));
				sender.sendMessage(i18n.tr("result", score));
			}
		});
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(future != null && !future.isDone()) {
			sender.sendMessage(i18n.tr("running"));
			return;
		}
		sender.sendMessage(i18n.tr("start"));
		this.cpuBenchmark(sender);
	}
}
