package cn.jiongjionger.neverlag.fixer;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

public class AntiMinecartPortal implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityPortal(EntityPortalEvent e) {
		if (!cm.isAntiMinecartPortal) {
			return;
		}
		if (e.getEntity() instanceof Minecart) {
			e.setCancelled(true);
		}
	}
}
