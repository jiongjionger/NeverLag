package cn.jiongjionger.neverlag.fixer;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.NeverLagUtils;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class AntiVillagerTrade implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onTouchEntity(PlayerInteractEntityEvent e) {
		if (!cm.isAntiVillagerTrade) {
			return;
		}
		Entity entity = e.getRightClicked();
		if (entity == null || NeverLagUtils.checkCustomNpc(entity)) {
			return;
		}
		World world = entity.getWorld();
		if (world == null) {
			return;
		}
		if (cm.disableVillagerTradeWorld.contains(world.getName())) {
			if (entity instanceof Villager) {
				e.setCancelled(true);
			}
		}
	}
}
