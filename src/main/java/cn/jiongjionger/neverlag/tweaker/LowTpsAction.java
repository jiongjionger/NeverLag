package cn.jiongjionger.neverlag.tweaker;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.cleaner.EntityCleaner;
import cn.jiongjionger.neverlag.cleaner.ItemCleaner;
import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.Bukkit;

public class LowTpsAction {

	private static final ConfigManager cm = ConfigManager.getInstance();
	private long lastActionTime = System.currentTimeMillis();

	public LowTpsAction() {
		NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (cm.lowTPSAction && NeverLag.getTpsWatcher().getAverageTPS() < cm.lowTPSLimit && lastActionTime + cm.lowTPSActionTimeLimit < System.currentTimeMillis()) {
					doAction();
				}
			}
		}, 20L, 20L);
	}

	private void doAction() {
		if (cm.lowTPSCleanEntity) {
			EntityCleaner.doClean();
		}
		if (cm.lowTPSCleanItem) {
			ItemCleaner.doClean();
		}
		for (String command : cm.lowTPSCommand) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
		}
		if (cm.lowTPSForceGC) {
			System.runFinalization();
			System.gc();
		}

	}
}
