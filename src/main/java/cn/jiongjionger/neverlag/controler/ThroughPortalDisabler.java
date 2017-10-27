package cn.jiongjionger.neverlag.controler;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.EntityUtils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPortalEvent;

public class ThroughPortalDisabler {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityPortal(EntityPortalEvent e) {
		if (!cm.isDisableEntityPortal) {
			return;
		}
		Entity entity = e.getEntity();
		if (entity == null || EntityUtils.checkCustomNpc(entity)) {
			return;
		}
		if ((entity instanceof Monster && cm.isDisableMonsterPortal)
			|| (entity instanceof Animals && cm.isDisableAnimalsPortal)
			|| (entity instanceof Item && cm.isDisableDropItemPortal)
			|| (entity instanceof Projectile && cm.isDisableProjectilePortal)) {
			e.setCancelled(true);
		}
	}
}
