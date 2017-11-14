package cn.jiongjionger.neverlag.controler;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.NeverLagUtils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPortalEvent;

public class ThroughPortalDisabler {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityPortal(EntityPortalEvent e) {
		if (!cm.portalDisallowEnabled) {
			return;
		}
		Entity entity = e.getEntity();
		if (entity == null || NeverLagUtils.checkCustomNpc(entity)) {
			return;
		}
		if ((entity instanceof Monster && cm.portalDisallowMonster)
			|| (entity instanceof Animals && cm.portalDisallowAnimals)
			|| (entity instanceof Item && cm.portalDisallowItem)
			|| (entity instanceof Projectile && cm.portalDisallowProjectile)) {
			e.setCancelled(true);
		}
	}
}
