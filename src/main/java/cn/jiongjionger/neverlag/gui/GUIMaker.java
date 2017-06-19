package cn.jiongjionger.neverlag.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GUIMaker {

	private final Inventory inv;

	public GUIMaker(Inventory inv) {
		if(inv.getType() == InventoryType.CHEST){  // 自定义大小的 Inventory
			this.inv = Bukkit.createInventory(inv.getHolder(), inv.getSize(), inv.getTitle());
		}else{  // 自定义类型的 Inventory
			this.inv = Bukkit.createInventory(inv.getHolder(), inv.getType(), inv.getTitle());
		}
		this.inv.setContents(inv.getContents());
	}

	public GUIMaker(int size, String title) {
		this.inv = Bukkit.createInventory(null, size, title);
	}

	/*
	 * public GUIMaker(InventoryType type, String title) { this.inv =
	 * Bukkit.createInventory(null, type, title); }
	 * 
	 * public GUIMaker(InventoryHolder owner, InventoryType type, String title)
	 * { this.inv = Bukkit.createInventory(owner, type, title); }
	 */

	public GUIMaker(InventoryHolder owner, int size, String title) {
		this.inv = Bukkit.createInventory(owner, size, title);
	}

	public GUIMaker(InventoryHolder owner, int size) {
		this.inv = Bukkit.createInventory(owner, size);
	}

	// 填充物品
	public GUIMaker fillItem(ItemStack item, int x, int y) {
		int slot = (y - 1) * 9 + x - 1;
		if (slot > this.inv.getSize() - 1) {
			return this;
		}
		this.inv.setItem(slot, item);
		return this;
	}

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

	public GUIMaker fillItem(ItemStack item, int slot) {
		if (slot > this.inv.getSize() - 1) {

			return this;
		}
		this.inv.setItem(slot, item);
		return this;
	}

	// 删除物品
	public GUIMaker removeItem(int x, int y) {
		int slot = (y - 1) * 9 + x - 1;
		this.inv.clear(slot);
		return this;
	}

	// 清空物品
	public GUIMaker clearItem() {
		this.inv.clear();
		return this;
	}

	// 填充空白物品
	public GUIMaker fillBlank(ItemStack item) {
		for (int i = 0; i < this.inv.getSize(); i++) {
			if (this.inv.getItem(i) == null || this.inv.getItem(i).getType().equals(Material.AIR)) {
				this.inv.setItem(i, item);
			}
		}
		return this;
	}

	// 填充间隔物品
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

	// 获取
	public Inventory get() {
		return this.inv;
	}

	// 获取行数
	public int getRow(int slot) {
		return slot / 9 + 1;
	}
}