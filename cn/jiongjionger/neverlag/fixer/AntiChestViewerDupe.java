package cn.jiongjionger.neverlag.fixer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiChestViewerDupe implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		if (!cm.isAntiChestViewerDupe()) {
			return;
		}
		if (e.getBlock() != null && e.getBlock().getState() instanceof InventoryHolder) {
			InventoryHolder inventory = (InventoryHolder) e.getBlock().getState();
			// 如果容器正在使用则不允许被破坏，以防止利用漏洞刷物品
			if (inventory.getInventory().getViewers().size() > 0) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(cm.getAntiChestViewerDupeMessage());
			}
		}
	}
}
