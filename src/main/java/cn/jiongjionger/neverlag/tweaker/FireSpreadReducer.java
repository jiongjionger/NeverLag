package cn.jiongjionger.neverlag.tweaker;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class FireSpreadReducer implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();
	private long lastfireSpread = System.currentTimeMillis();

	@EventHandler
	public void fireSpread(final BlockIgniteEvent e) {
		if (!cm.isReduceFireSpread) {
			return;
		}
		if (BlockIgniteEvent.IgniteCause.SPREAD.equals(e.getCause())) {
			long now = System.currentTimeMillis();
			if (now > this.lastfireSpread + cm.reduceFireSpreadTime) {
				this.lastfireSpread = now;
			} else {
				e.setCancelled(true);
			}
		}
	}
}
