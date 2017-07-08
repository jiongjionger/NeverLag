package cn.jiongjionger.neverlag.fixer;

import org.bukkit.World;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiNetherHopper implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onHopper(InventoryMoveItemEvent e) {
		if (cm.isAntiNetherHopper()) {
			if (e.getInitiator().getHolder() instanceof Hopper) {
				Hopper hopper = (Hopper) e.getInitiator().getHolder();
				if (hopper.getWorld().getEnvironment() == World.Environment.NETHER) {
					e.setCancelled(true);
				}
			}
		}
	}
}
