package cn.jiongjionger.neverlag.tweaker;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class NoTNTChainReaction implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onTNTExplode(EntityExplodeEvent e) {
		if (!cm.isNoTNTChainReaction()) {
			return;
		}
		Entity entity = e.getEntity();
		// if (entity == null) {  // instanceof 自带 null 检查
		//	return;
		// }
		if (entity instanceof TNTPrimed) {
			for (Entity primedTNT : entity.getNearbyEntities(4.5, 4.5, 4.5)) {
				if (primedTNT instanceof TNTPrimed) {
					primedTNT.remove();
				}
			}
		}
	}
}
