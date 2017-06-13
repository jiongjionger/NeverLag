package cn.jiongjionger.neverlag.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class GUISortPingListener implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	private void onQuit(PlayerQuitEvent e) {
		GUISortPingHolder.clear(e.getPlayer());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	private void onClickGUI(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		if (inv == null) {
			return;
		}
		if (cm.getGuiPingTitle().equals(inv.getTitle())) {
			e.setCancelled(true);
		}
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			int slot = e.getRawSlot();
			if (slot < 0 || slot > inv.getSize()) {
				return;
			}
			GUISortPing guiSortPing = GUISortPingHolder.get(p);
			if (guiSortPing != null) {
				if (slot == 44) {
					p.openInventory(guiSortPing.getPrePage());
				} else if (slot == 53) {
					p.openInventory(guiSortPing.getNextPage());
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onGUIClose(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		if (inv == null) {
			return;
		}
		if (cm.getGuiPingTitle().equals(inv.getTitle())) {
			GUISortPingHolder.clear(e.getPlayer().getName());
		}
	}
}
