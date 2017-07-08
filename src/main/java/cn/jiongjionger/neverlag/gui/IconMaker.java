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

	public IconMaker(ItemStack item) {
		this.item = item.clone();
	}

	public IconMaker(Material type) {
		this.item = new ItemStack(type);
	}

	public IconMaker(String type) {
		try {
			Material m = Material.valueOf(type);
			this.item = new ItemStack(m);
		} catch (IllegalArgumentException | NullPointerException e) {
			this.item = new ItemStack(Material.STONE);
		}
	}

	/*
	 * 获取ItemStack实例
	 * 
	 * @return 返回物品
	 */
	public ItemStack getItem() {
		return this.item;
	}

	/*
	 * 替换物品的Lore
	 * 
	 * @param oldlore 需要被替换的lore
	 * 
	 * @param newlore 新的lore
	 * 
	 * @return 返回自身的IconMaker实例以实现链式调用方法
	 */
	public IconMaker replaceLore(String oldlore, String newlore) {
		if (this.item != null && this.item.hasItemMeta() && this.item.getItemMeta().hasLore()) {
			oldlore = ChatColor.translateAlternateColorCodes('&', oldlore);
			newlore = ChatColor.translateAlternateColorCodes('&', newlore);

			List<String> colorLore = new ArrayList<>();
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

	/*
	 * 设置物品的数量
	 * 
	 * @param amount 数量
	 * 
	 * @return 返回自身的IconMaker实例以实现链式调用方法
	 */
	public IconMaker setAmount(int amount) {
		this.item.setAmount(amount);
		return this;
	}

	/*
	 * 设置物品的显示名称
	 * 
	 * @param name 显示名称
	 * 
	 * @return 返回自身的IconMaker实例以实现链式调用方法
	 */
	public IconMaker setDisplayName(String name) {
		ItemMeta m = this.item.getItemMeta();
		m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		this.item.setItemMeta(m);
		return this;
	}

	/*
	 * 设置物品的耐久度、羊毛等可以利用这个设置颜色
	 * 
	 * @param d 耐久度
	 * 
	 * @return 返回自身的IconMaker实例以实现链式调用方法
	 */
	public IconMaker setDurability(short d) {
		this.item.setDurability(d);
		return this;
	}

	/*
	 * 设置物品的lore
	 * 
	 * @param lore 被设置的lore
	 * 
	 * @return 返回自身的IconMaker实例以实现链式调用方法
	 */
	public IconMaker setLore(List<String> lore) {
		List<String> colorLore = new ArrayList<>();
		for (String l : lore) {
			colorLore.add(ChatColor.translateAlternateColorCodes('&', l));
		}
		ItemMeta m = this.item.getItemMeta();
		m.setLore(colorLore);
		this.item.setItemMeta(m);
		return this;
	}

	/*
	 * 设置物品的lore
	 * 
	 * @param lore 被设置的lore
	 * 
	 * @return 返回自身的IconMaker实例以实现链式调用方法
	 */
	public IconMaker setLore(String... lore) {
		List<String> colorLore = new ArrayList<>();
		for (String l : lore) {
			colorLore.add(ChatColor.translateAlternateColorCodes('&', l));
		}
		ItemMeta m = this.item.getItemMeta();
		m.setLore(colorLore);
		this.item.setItemMeta(m);
		return this;
	}

	/*
	 * 设置头颅的名称以显示皮肤
	 * 
	 * @param username 玩家名字
	 * 
	 * @return 返回自身的IconMaker实例以实现链式调用方法
	 */
	public IconMaker setOwner(String username) {
		if (this.item != null && this.item.getType().equals(Material.SKULL_ITEM)) {
			this.item.setDurability((short) 3); // 3: Player
			SkullMeta sm = (SkullMeta) item.getItemMeta();
			sm.setOwner(username);
			item.setItemMeta(sm);
		}
		return this;
	}
}
