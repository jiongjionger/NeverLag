package cn.jiongjionger.neverlag.fixer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;


public class AntiBonemealDupeItem implements Listener {
	
	private final ConfigManager cm = ConfigManager.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onUseBonemeal(PlayerInteractEvent e) {
		if (cm.isAntiBonemealDupe() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (!e.getItem().getType().equals(Material.INK_SACK)) {
				return;
			}
			if (cm.getAntiBonemealBlackList().contains(e.getClickedBlock().getTypeId())) {
				e.setCancelled(true);
			}
		}
	}
}
