package cn.jiongjionger.neverlag.tweaker;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NoTNTChainReaction implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onTNTExplode(EntityExplodeEvent e) {
		if (!cm.disableChainReaction) {
			return;
		}
		Entity entity = e.getEntity();
		if (entity instanceof TNTPrimed) {
			for (Entity primedTNT : entity.getNearbyEntities(4.5, 4.5, 4.5)) {
				if (primedTNT instanceof TNTPrimed) {
					primedTNT.remove();
				}
			}
		}
	}
}
