package cn.jiongjionger.neverlag.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class IconMaker {
	private ItemStack item;

	public IconMaker(String type) {
		try {
			Material m = Material.valueOf(type);
			this.item = new ItemStack(m);
		} catch (Exception e) {
			this.item = new ItemStack(Material.STONE);
		}
	}

	public IconMaker(Material type) {
		this.item = new ItemStack(type);
	}

	public IconMaker(ItemStack item) {
		this.item = item.clone();
	}

	// 设置显示名称
	public IconMaker setDisplayName(String name) {
		ItemMeta m = this.item.getItemMeta();
		m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		this.item.setItemMeta(m);
		return this;
	}

	// 设置Lore
	public IconMaker setLore(List<String> lore) {
		List<String> colorLore = new ArrayList<String>();
		for (String l : lore) {
			colorLore.add(ChatColor.translateAlternateColorCodes('&', l));
		}
		ItemMeta m = this.item.getItemMeta();
		m.setLore(colorLore);
		this.item.setItemMeta(m);
		return this;
	}

	// 设置Lore
	public IconMaker setLore(String... lore) {
		List<String> colorLore = new ArrayList<String>();
		for (String l : lore) {
			colorLore.add(ChatColor.translateAlternateColorCodes('&', l));
		}
		ItemMeta m = this.item.getItemMeta();
		m.setLore(colorLore);
		this.item.setItemMeta(m);
		return this;
	}

	// 设置耐久度，羊毛等通过这个设置颜色
	public IconMaker setDurability(short d) {
		this.item.setDurability(d);
		return this;
	}

	// 设置数量
	public IconMaker setAmount(int amount) {
		this.item.setAmount(amount);
		return this;
	}

	// 替换Lore的内容
	public IconMaker replaceLore(String oldlore, String newlore) {
		if (this.item != null && this.item.hasItemMeta() && this.item.getItemMeta().hasLore()) {
			oldlore = ChatColor.translateAlternateColorCodes('&', oldlore);
			newlore = ChatColor.translateAlternateColorCodes('&', newlore);

			List<String> colorLore = new ArrayList<String>();
			for (String l : this.item.getItemMeta().getLore()) {
				colorLore.add(l.replace(oldlore, newlore));
			}

			ItemMeta m = this.item.getItemMeta();
			m.setLore(colorLore);
			this.item.setItemMeta(m);
			return this;
		}
		return this;
	}

	// 设置头颅
	public IconMaker setOwner(String username) {
		if (this.item != null && this.item.getType().equals(Material.SKULL_ITEM)) {
			this.item.setDurability((short) 3);
			SkullMeta sm = (SkullMeta) item.getItemMeta();
			sm.setOwner(username);
			item.setItemMeta(sm);
		}
		return this;
	}

	// 获取Item
	public ItemStack getItem() {
		return this.item;
	}
}
