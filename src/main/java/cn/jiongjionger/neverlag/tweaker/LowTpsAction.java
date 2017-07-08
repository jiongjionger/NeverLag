package cn.jiongjionger.neverlag.tweaker;

import org.bukkit.Bukkit;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.cleaner.EntityCleaner;
import cn.jiongjionger.neverlag.cleaner.ItemCleaner;
import cn.jiongjionger.neverlag.config.ConfigManager;

public class LowTpsAction {

	private static ConfigManager cm = ConfigManager.getInstance();
	private long lastActionTime = System.currentTimeMillis();

	public LowTpsAction() {
		NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (cm.isLowTPSAction() && NeverLag.getTpsWatcher().getAverageTPS() < cm.getLowTPSLimit() && lastActionTime + cm.getLowTPSActionTimeLimit() < System.currentTimeMillis()) {
					doAction();
				}
			}
		}, 20L, 20L);
	}

	private void doAction() {
		if (cm.isLowTPSCleanEntity()) {
			EntityCleaner.doClean();
		}
		if (cm.isLowTPSCleanItem()) {
			ItemCleaner.doClean();
		}
		for (String command : cm.getLowTPSCommand()) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
		}
		// 显性调用GC是非常没必要的，只适合一些非常特殊的应用场景，请测试你的服务端是否需要这个功能
		if (cm.isLowTPSForceGC()) {
			System.runFinalization();
			System.gc();
		}

	}
}
