package cn.jiongjionger.neverlag.gui;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class GUISortPingListener implements Listener {

	private final I18n i18n = NeverLag.i18n("gui.ping");

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onClickGUI(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		if (inv == null) {
			return;
		}
		if (i18n.tr("title").equals(inv.getTitle())) {
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
					p.openInventory(guiSortPing.getPreviousPage());
				} else if (slot == 53) {
					p.openInventory(guiSortPing.getNextPage());
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onGUIClose(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		if (inv == null) {
			return;
		}
		if (i18n.tr("title").equals(inv.getTitle())) {
			GUISortPingHolder.clear(e.getPlayer().getName());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onQuit(PlayerQuitEvent e) {
		GUISortPingHolder.clear(e.getPlayer());
	}
}
