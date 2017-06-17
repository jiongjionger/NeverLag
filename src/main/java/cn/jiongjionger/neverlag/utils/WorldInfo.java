package cn.jiongjionger.neverlag.utils;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;

public class WorldInfo {
	
	// 世界名字
	private String worldName;
	// 在线玩家
	private int totalOnline;
	// 加载的区块总数
	private int totalChunk;
	// 实体总数量
	private int totalEntity;
	// 动物总数量
	private int totalAnimals;
	// 怪物总数量
	private int totalMonsters;
	// 掉落物总数量
	private int totalDropItem;
	// Tiles总数量
	private int totalTiles;
	// 箱子总数量
	private int totalChest;
	// 漏斗总数量
	private int totalHopper;
	// 熔炉总数量
	private int totalFurnace;
	// 发射器总数量
	private int totalDispenser;
	// 投掷器总数量
	private int totalDropper;
	// 酿造台总数量
	private int totalBrewingStand;

	public WorldInfo(World world) {
		this.worldName = world.getName();
		this.totalOnline = world.getPlayers().size();
		for (Entity entity : world.getEntities()) {
			this.totalEntity++;
			if (entity instanceof Animals) {
				this.totalAnimals++;
			} else if (entity instanceof Monster) {
				this.totalMonsters++;
			} else if (entity instanceof Item) {
				this.totalDropItem++;
			}
		}
		for (Chunk loadedChunk : world.getLoadedChunks()) {
			this.totalChunk++;
			for (BlockState tiles : loadedChunk.getTileEntities()) {
				this.totalTiles++;
				if (tiles instanceof Hopper) {
					this.totalHopper++;
				} else if (tiles instanceof Chest) {
					this.totalChest++;
				} else if (tiles instanceof Dispenser) {
					this.totalDispenser++;
				} else if (tiles instanceof Dropper) {
					this.totalDropper++;
				} else if (tiles instanceof BrewingStand) {
					this.totalBrewingStand++;
				}
			}

		}
	}

	public int getTotalEntity() {
		return totalEntity;
	}

	public void setTotalEntity(int totalEntity) {
		this.totalEntity = totalEntity;
	}

	public int getTotalAnimals() {
		return totalAnimals;
	}

	public void setTotalAnimals(int totalAnimals) {
		this.totalAnimals = totalAnimals;
	}

	public int getTotalMonsters() {
		return totalMonsters;
	}

	public void setTotalMonsters(int totalMonsters) {
		this.totalMonsters = totalMonsters;
	}

	public int getTotalTiles() {
		return totalTiles;
	}

	public void setTotalTiles(int totalTiles) {
		this.totalTiles = totalTiles;
	}

	public int getTotalChest() {
		return totalChest;
	}

	public void setTotalChest(int totalChest) {
		this.totalChest = totalChest;
	}

	public int getTotalHopper() {
		return totalHopper;
	}

	public void setTotalHopper(int totalHopper) {
		this.totalHopper = totalHopper;
	}

	public int getTotalFurnace() {
		return totalFurnace;
	}

	public void setTotalFurnace(int totalFurnace) {
		this.totalFurnace = totalFurnace;
	}

	public int getTotalDispenser() {
		return totalDispenser;
	}

	public void setTotalDispenser(int totalDispenser) {
		this.totalDispenser = totalDispenser;
	}

	public int getTotalDropper() {
		return totalDropper;
	}

	public void setTotalDropper(int totalDropper) {
		this.totalDropper = totalDropper;
	}

	public int getTotalBrewingStand() {
		return totalBrewingStand;
	}

	public void setTotalBrewingStand(int totalBrewingStand) {
		this.totalBrewingStand = totalBrewingStand;
	}

	public int getTotalDropItem() {
		return totalDropItem;
	}

	public void setTotalDropItem(int totalDropItem) {
		this.totalDropItem = totalDropItem;
	}

	public int getTotalOnline() {
		return totalOnline;
	}

	public void setTotalOnline(int totalOnline) {
		this.totalOnline = totalOnline;
	}

	public int getTotalChunk() {
		return totalChunk;
	}

	public void setTotalChunk(int totalChunk) {
		this.totalChunk = totalChunk;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
}
