package cn.jiongjionger.neverlag.gui;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.utils.PingUtils;

public class GUISortPing {

	private final I18n i18n = NeverLag.i18n("gui.ping");
	private GUIMaker guiMaker;
	private int slot = 0;
	private Map<String, Integer> content;
	private int page;
	private int maxPage;

	public GUISortPing(Map<String, Integer> content) {
		this.content = content;
		this.page = 1;
		this.maxPage = Integer.parseInt(new DecimalFormat("#").format(content.size() / 45)); // 45:
	}

	private void drawPageGUI() {
		this.guiMaker.fillItem(new IconMaker(Material.STAINED_GLASS_PANE)
				.setDurability((short) 5) // 5: Lime
				.setDisplayName(i18n.tr("previous"))
				.getItem(), 1, 6);
		this.guiMaker.fillItem(new IconMaker(Material.STAINED_GLASS_PANE)
				.setDurability((short) 5) // 5: Lime
				.setDisplayName(i18n.tr("next"))
				.getItem(), 9, 6);
	}

	public Inventory get() {
		return this.get(1);
	}

	public Inventory get(int page) {
		this.guiMaker = new GUIMaker(54, i18n.tr("title"));
		this.slot = 0;
		this.drawPageGUI();
		int startPos = (page - 1) * 45;
		int endPos = page * 45;
		int pos = 0;
		Iterator<Entry<String, Integer>> iterator = this.content.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			pos++;
			if (pos >= startPos && pos <= endPos) {
				this.put(entry.getKey(), PingUtils.colorPing(entry.getValue()));
			}
		}
		return this.guiMaker.get();
	}

	public Inventory getNextPage() {
		if (this.page >= this.maxPage) {
			if (this.guiMaker != null) {
				return this.guiMaker.get();
			} else {
				return get(this.page);
			}
		} else {
			this.page++;
			return get(this.page);
		}
	}

	public Inventory getPreviousPage() {
		if (this.page <= 1) {
			if (this.guiMaker != null) {
				return this.guiMaker.get();
			} else {
				return get(1);
			}
		}
		this.page--;
		return get(this.page);
	}

	public void put(String username, String ping) {
		if (this.slot > 44) {
			return;
		}
		this.guiMaker.fillItem(new IconMaker(Material.SKULL_ITEM)
				.setOwner(username)
				.setDisplayName(i18n.tr("item.name", username))
				.setLore(i18n.tr("item.lore", ping))
				.getItem(), this.slot);
		this.slot++;
	}
}
