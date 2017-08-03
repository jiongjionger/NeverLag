package cn.jiongjionger.neverlag.fixer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiNegativeItemDupe implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	// 投掷器/发射器
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockDispense(BlockDispenseEvent e) {
		if (!cm.isAntiNegativeItemDupe) {
			return;
		}
		Inventory content = null;
		if (e.getBlock().getState() instanceof InventoryHolder) {
			content = ((InventoryHolder) e.getBlock().getState()).getInventory();
		}
		if (content == null || content.getSize() == 0) {
			return;
		}
		for (ItemStack item : content.getContents()) {
			if (item != null && !item.getType().equals(Material.AIR)) {
				if (item.getAmount() <= 0) {
					item.setType(Material.AIR);
				}
			}
		}
	}

	// 点击容器
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onClick(InventoryClickEvent e) {
		if (!cm.isAntiNegativeItemDupe) {
			return;
		}
		ItemStack item = null;
		if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
			item = e.getCurrentItem();
		} else if (e.getCursor() != null && !e.getCursor().getType().equals(Material.AIR)) {
			item = e.getCursor();
		}
		if (item != null && item.getAmount() <= 0) {
			item.setType(Material.AIR);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onSpawn(ItemSpawnEvent e) {
		if (cm.isAntiNegativeItemDupe && e.getEntity() != null && e.getEntity().getItemStack() != null && e.getEntity().getItemStack().getAmount() <= 0) {
			e.setCancelled(true);
		}
	}
}
