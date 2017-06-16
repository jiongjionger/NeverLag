package cn.jiongjionger.neverlag.tweaker;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class NoHighFallWater implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onBlockFromTo(BlockFromToEvent e) {
		if (!cm.isNoHighFallWater()) {
			return;
		}
		Block to = e.getToBlock();
		if (to == null) {
			return;
		}
		if (e.getToBlock().getLocation().getBlockY() <= 63) {
			return;
		}
		if (isAirBottom(to, cm.getNoHighFallWaterDistance())) {
			e.setCancelled(true);
		}
	}

	private boolean isAirBottom(Block b, int checkDistance) {
		Block nowCheckBlock = b;
		while (checkDistance-- > 0) {
			nowCheckBlock = nowCheckBlock.getRelative(BlockFace.DOWN);
			if (nowCheckBlock != null && !Material.AIR.equals(nowCheckBlock.getType())) {
				return false;
			}
		}
		return true;
	}
}
