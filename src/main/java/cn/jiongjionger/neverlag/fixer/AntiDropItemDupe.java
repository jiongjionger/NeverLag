package cn.jiongjionger.neverlag.fixer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiDropItemDupe implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDrop(PlayerDropItemEvent e) {
		if (!cm.isAntiDropItemDupe()) {
			return;
		}
		if (e.getPlayer() == null || !e.getPlayer().isOnline() || !e.getPlayer().isValid()) {
			e.setCancelled(true);
		}
	}
}
