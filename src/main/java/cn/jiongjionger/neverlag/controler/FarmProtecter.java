package cn.jiongjionger.neverlag.controler;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class FarmProtecter implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	// 防止跳跃破坏农田
	// 将优先级设为NORMAL以与各种小游戏插件兼容. LOWEST可能破坏一些小游戏的游戏机制
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onJump(PlayerInteractEvent e) {
		if (!cm.isFarmProtect) {
			return;
		}

		// 防止农作物被踩踏
		if (e.getAction().equals(Action.PHYSICAL)) {
			if (e.getClickedBlock().getType().equals(Material.SOIL)) {
				e.setCancelled(true);
			}
		}
	}

	// 防止怪物破坏农作物
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onMobJump(EntityInteractEvent e) {
		if (!cm.isFarmProtect) {
			return;
		}
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			return;
		}
		if (e.getBlock().getType().equals(Material.SOIL)) {
			e.setCancelled(true);
		}
	}
}
