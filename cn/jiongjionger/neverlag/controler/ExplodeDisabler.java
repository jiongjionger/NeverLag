package cn.jiongjionger.neverlag.controler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class ExplodeDisabler implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onExplosion(EntityExplodeEvent e) {
		if (cm.isDisableExplode()) {
			e.blockList().clear();
		}
	}

}
