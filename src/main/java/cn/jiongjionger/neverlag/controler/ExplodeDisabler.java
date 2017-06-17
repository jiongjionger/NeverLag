package cn.jiongjionger.neverlag.controler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.event.block.BlockExplodeEvent;

public class ExplodeDisabler implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	// 将优先级设为NORMAL以与各种小游戏插件兼容. LOWEST可能破坏一些小游戏的游戏机制
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onExplosion(EntityExplodeEvent e) {
		if (cm.isDisableExplode()) {
			e.blockList().clear();
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockExplosion(BlockExplodeEvent e) {
		if (cm.isDisableExplode()) {
			e.blockList().clear();
		}
	}
}
