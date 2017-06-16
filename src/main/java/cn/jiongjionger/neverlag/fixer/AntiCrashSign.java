package cn.jiongjionger.neverlag.fixer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiCrashSign implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onSignChange(SignChangeEvent e) {
		if (!cm.isAntiCrashSign()) {
			return;
		}
		for (int i = 0; i < 4; i++) {
			if (e.getLine(i).length() > 50) {
				e.setCancelled(true);
				break;
			}
		}
	}
}
