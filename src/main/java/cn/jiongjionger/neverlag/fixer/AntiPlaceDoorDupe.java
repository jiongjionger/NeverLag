package cn.jiongjionger.neverlag.fixer;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiPlaceDoorDupe implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	// 手里拿着门无法捡甘蔗
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPickup(PlayerPickupItemEvent e) {
		if (!cm.isAntiPlaceDoorDupe()) {
			return;
		}
		if (e.getPlayer().getItemInHand().getType() == Material.WOOD_DOOR || e.getPlayer().getItemInHand().getType() == Material.IRON_DOOR) {
			if (e.getItem().getItemStack().getType() == Material.SUGAR_CANE || e.getItem().getItemStack().getType() == Material.CACTUS) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		if (!cm.isAntiPlaceDoorDupe()) {
			return;
		}
		// 判断手里的物品是否为门
		if (e.getItemInHand().getType() == Material.WOOD_DOOR || e.getItemInHand().getType() == Material.IRON_DOOR) {
			// 清理所在区块的甘蔗掉落物
			for (Entity entity : e.getPlayer().getLocation().getChunk().getEntities()) {
				if (entity instanceof Item) {
					Material itemType = ((Item) entity).getItemStack().getType();
					if (itemType.equals(Material.SUGAR_CANE) || itemType.equals(Material.CACTUS)) {
						entity.remove();
					}
				}
			}
		}
	}
}
