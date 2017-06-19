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
		if (cm.isAntiInfiniteRail() && this.isDupeBlock(e.getChangedType()) && this.isRails(e.getBlock().getType())) {
			e.setCancelled(true);
		}
	}

	/*
	 * 判断是否为铁轨材质
	 * 
	 * @param type 材质类型
	 * 
	 * @return 是否为铁轨材质
	 */
	private boolean isRails(Material type) {
		if (Material.RAILS.equals(type) || Material.DETECTOR_RAIL.equals(type) || Material.POWERED_RAIL.equals(type) || Material.ACTIVATOR_RAIL.equals(type)) {
			return true;
		}
		return false;
	}

	/*
	 * 判断是否为可以刷铁轨的材质
	 * 
	 * @param type 材质类型
	 * 
	 * @return 是否为可以刷铁轨的材质
	 */
	@SuppressWarnings("deprecation")
	private boolean isDupeBlock(Material type) {
		if (Material.PISTON_EXTENSION.equals(type) || Material.PISTON_STICKY_BASE.equals(type) || Material.PISTON_BASE.equals(type)
				|| Material.PISTON_MOVING_PIECE.equals(type) || Material.PUMPKIN.equals(type) || Material.DISPENSER.equals(type)
				|| Material.DROPPER.equals(type) || type.getId() == 165) {
			return true;
		}
		return false;
	}
}
