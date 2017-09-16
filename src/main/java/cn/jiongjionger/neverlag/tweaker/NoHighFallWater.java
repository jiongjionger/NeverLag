package cn.jiongjionger.neverlag.tweaker;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class NoHighFallWater implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	private boolean isAirBottom(Block b, int checkDistance) {
		Block nowCheckBlock = b;
		while (checkDistance-- > 0) {
			nowCheckBlock = nowCheckBlock.getRelative(BlockFace.DOWN);
			if (nowCheckBlock != null) {
				return false;
			}
		}
		return true;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onBlockFromTo(BlockFromToEvent e) {
		if (!cm.noHighFallWater) {
			return;
		}
		Block to = e.getToBlock();
		if (to == null) {
			return;
		}
		if (e.getToBlock().getLocation().getBlockY() <= 63) {
			return;
		}
		if (isAirBottom(to, cm.noHighFallWaterDistance)) {
			e.setCancelled(true);
		}
	}
}
