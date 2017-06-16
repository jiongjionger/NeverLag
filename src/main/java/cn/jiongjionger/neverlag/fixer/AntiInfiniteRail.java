package cn.jiongjionger.neverlag.fixer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiInfiniteRail implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent e) {
		if (cm.isAntiInfiniteRail() && e.getChangedType().equals(Material.RAILS)) {
			Material type = e.getBlock().getType();
			if (type.equals(Material.DETECTOR_RAIL) || type.equals(Material.POWERED_RAIL) || type.equals(Material.ACTIVATOR_RAIL)) {
				e.setCancelled(true);
			}
		}
	}
}
