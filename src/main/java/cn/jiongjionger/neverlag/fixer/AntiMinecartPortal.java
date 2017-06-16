package cn.jiongjionger.neverlag.fixer;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiMinecartPortal implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityPortal(EntityPortalEvent e) {
		if (!cm.isAntiMinecartPortal()) {
			return;
		}
		if (e.getEntityType() == EntityType.MINECART
				|| e.getEntityType() == EntityType.MINECART_CHEST
				|| e.getEntityType() == EntityType.MINECART_FURNACE
				|| e.getEntityType() == EntityType.MINECART_HOPPER
				|| e.getEntityType() == EntityType.MINECART_TNT) {
			e.setCancelled(true);
		}
	}
}
