package cn.jiongjionger.neverlag.controler;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPortalEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class ThroughPortalDisabler {

	private ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityPortal(EntityPortalEvent e) {
		if (!cm.isDisableEntityPortal()) {
			return;
		}
		Entity entity = e.getEntity();
		if (entity == null || entity.hasMetadata("NPC") || entity.hasMetadata("MyPet")) {
			return;
		}
		if (entity instanceof Monster && cm.isDisableMonsterPortal()) {
			e.setCancelled(true);
		} else if (entity instanceof Animals && cm.isDisableAnimalsPortal()) {
			e.setCancelled(true);
		} else if (entity instanceof Item && cm.isDisableDropItemPortal()) {
			e.setCancelled(true);
		} else if (entity instanceof Projectile && cm.isDisableProjectilePortal()) {
			e.setCancelled(true);
		}
	}
}
