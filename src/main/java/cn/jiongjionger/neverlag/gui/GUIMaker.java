package cn.jiongjionger.neverlag.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GUIMaker {

	private final Inventory inv;

	public GUIMaker(int size, String title) {
		this.inv = Bukkit.createInventory(null, size, title);
	}

	public GUIMaker(Inventory inv) {
		if (inv.getType() == InventoryType.CHEST) { // 自定义大小的 Inventory
			this.inv = Bukkit.createInventory(inv.getHolder(), inv.getSize(), inv.getTitle());
		} else { // 自定义类型的 Inventory
			this.inv = Bukkit.createInventory(inv.getHolder(), inv.getType(), inv.getTitle());
		}
		this.inv.setContents(inv.getContents());
	}

	/*
	 * public GUIMaker(InventoryType type, String title) { this.inv =
	 * Bukkit.createInventory(null, type, title); }
	 * 
	 * public GUIMaker(InventoryHolder owner, InventoryType type, String title)
	 * { this.inv = Bukkit.createInventory(owner, type, title); }
	 */

	public GUIMaker(InventoryHolder owner, int size) {
		this.inv = Bukkit.createInventory(owner, size);
	}

	public GUIMaker(InventoryHolder owner, int size, String title) {
		this.inv = Bukkit.createInventory(owner, size, title);
	}

	/*
	 * 清空GUI内的所有物品
	 * 
	 * @return 返回自身的GUIMaker实例以实现链式调用方法
	 */
	public GUIMaker clearItem() {
		this.inv.clear();
		return this;
	}

	/*
	 * 将GUI内的空白处填入传参的物品
	 * 
	 * @param 需要填充的物品
	 * 
	 * @return 返回自身的GUIMaker实例以实现链式调用方法
	 */
	public GUIMaker fillBlank(ItemStack item) {
		for (int i = 0; i < this.inv.getSize(); i++) {
			if (this.inv.getItem(i) == null || this.inv.getItem(i).getType().equals(Material.AIR)) {
				this.inv.setItem(i, item);
			}
		}
		return this;
	}

	/*
	 * 通过传入GUI内的位置设置物品
	 * 
	 * @param item 需要设置的物品
	 * 
	 * @param slot GUI内的位置
	 * 
	 * @return 返回自身的GUIMaker实例以实现链式调用方法
	 */
	public GUIMaker fillItem(ItemStack item, int slot) {
		if (slot > this.inv.getSize() - 1) {

			return this;
		}
		this.inv.setItem(slot, item);
		return this;
	}

	/*
	 * 通过坐标来设置GUI内的物品
	 * 
	 * @param item 需要设置的物品
	 * 
	 * @param x 列数
	 * 
	 * @param y 行数
	 * 
	 * @return 返回自身的GUIMaker实例以实现链式调用方法
	 */
	public GUIMaker fillItem(ItemStack item, int x, int y) {
		int slot = (y - 1) * 9 + x - 1;
		if (slot > this.inv.getSize() - 1) {
			return this;
		}
		this.inv.setItem(slot, item);
		return this;
	}

	/*
	 * 通过传入多个GUI内的坐标来设置多个物品
	 * 
	 * @param item 需要设置的物品
	 * 
	 * @param pos 坐标（列数、行数）
	 * 
	 * @return 返回自身的GUIMaker实例以实现链式调用方法
	 */
	public GUIMaker fillItem(ItemStack item, int[]... pos) {
		for (int[] nowpos : pos) {
			if (nowpos.length == 2) {
				int slot = (nowpos[1] - 1) * 9 + nowpos[0] - 1;
				if (slot > this.inv.getSize() - 1) {
					return this;
				}
				this.inv.setItem(slot, item);
			}
		}
		return this;
	}

	/*
	 * 填充间隔物品，GUI内的物品左右会被填充传参的物品
	 * 
	 * @param item 需要填充的物品
	 * 
	 * @return 返回自身的GUIMaker实例以实现链式调用方法
	 */
	public GUIMaker fillWhite(ItemStack item) {
		for (int i = 0; i < this.inv.getSize(); i++) {
			if (this.inv.getItem(i) != null && !this.inv.getItem(i).getType().equals(Material.AIR) && !this.inv.getItem(i).isSimilar(item)) {
				int left_slot = i - 1;
				int right_slot = i + 1;
				if (getRow(i) == getRow(left_slot) && left_slot >= 0) {
					if (this.inv.getItem(left_slot) == null || this.inv.getItem(left_slot).getType().equals(Material.AIR)) {
						this.inv.setItem(left_slot, item);
					}
				}
				if (getRow(i) == getRow(right_slot) && right_slot <= this.inv.getSize() - 1) {
					if (this.inv.getItem(right_slot) == null || this.inv.getItem(right_slot).getType().equals(Material.AIR)) {
						this.inv.setItem(right_slot, item);
					}
				}
			}
		}
		return this;
	}

	/*
	 * 获取Inventory实例
	 * 
	 * @return Inventory实例
	 */
	public Inventory get() {
		return this.inv;
	}

	/*
	 * 根据容器内的位置来获取行数
	 * 
	 * @param slot 容器内的位置
	 * 
	 * @return 行数
	 */
	public int getRow(int slot) {
		return slot / 9 + 1;
	}

	/*
	 * 根据坐标删除GUI内的物品
	 * 
	 * @param x GUI内的列数
	 * 
	 * @param y GUI内的行数
	 * 
	 * @return 返回自身的GUIMaker实例以实现链式调用方法
	 */
	public GUIMaker removeItem(int x, int y) {
		int slot = (y - 1) * 9 + x - 1;
		this.inv.clear(slot);
		return this;
	}
}