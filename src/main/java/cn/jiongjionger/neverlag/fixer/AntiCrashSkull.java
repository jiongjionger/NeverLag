package cn.jiongjionger.neverlag.fixer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.VersionUtils;

public class AntiCrashSkull implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockFromTo(BlockFromToEvent e) {
		if (cm.isAntiCrashSkull && VersionUtils.isLowerThan(VersionUtils.V1_9) && e.getToBlock().getType().equals(Material.SKULL)) {
			e.setCancelled(true);
		}
	}
}
