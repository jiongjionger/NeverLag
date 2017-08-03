package cn.jiongjionger.neverlag.fixer;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.VersionUtils;

public class AntiPMM implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPistonExtend(BlockPistonExtendEvent e) {
		if (cm.isDisablePMM && VersionUtils.isAtLeast(VersionUtils.V1_8)) {
			// 使用typeid是为了兼容1.8以下的服务端
			for (Block b : e.getBlocks()) {
				if (b.getTypeId() == 165) { // 165 - SLIME_BLOCK
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}
