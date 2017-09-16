package cn.jiongjionger.neverlag.fixer;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class AntiInfiniteRail implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@SuppressWarnings("deprecation")
	private boolean isDupeBlock(Material type) {
		return Material.PISTON_EXTENSION == type || Material.PISTON_STICKY_BASE == type || Material.PISTON_BASE == type
			|| Material.PISTON_MOVING_PIECE == type || Material.PUMPKIN == type || Material.DISPENSER == type
			|| Material.DROPPER == type || type.getId() == 165;
	}

	private boolean isRails(Material type) {
		return Material.RAILS == type || Material.DETECTOR_RAIL == type || Material.POWERED_RAIL == type || Material.ACTIVATOR_RAIL == type;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent e) {
		if (cm.isAntiInfiniteRail && this.isDupeBlock(e.getChangedType()) && this.isRails(e.getBlock().getType())) {
			e.setCancelled(true);
		}
	}
}
