package cn.jiongjionger.neverlag.controler;

import java.util.HashSet;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.PermUtils;

public class TilesLimiter implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	// 限制漏斗
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlaceHopper(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles()) {
			return;
		}
		if (e.getBlock().getType().equals(Material.HOPPER)) {
			Player p = e.getPlayer();
			if (p.isOp()) {
				return;
			}
			int limit = PermUtils.getMaxPerm(p, "NeverLag.LimitHopper.");
			if (limit <= 0) {
				limit = cm.getLimitTilesHopperDefault();
			}
			if (isLimit(e.getBlock().getLocation(), Material.HOPPER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.getLimitTilesMessage());
			}
		}
	}

	// 限制活塞
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlacePistion(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles()) {
			return;
		}
		Player p = e.getPlayer();
		if (p.isOp()) {
			return;
		}
		Material type = e.getBlock().getType();
		Material checkType = null;
		if (type.equals(Material.PISTON_BASE)) {
			checkType = Material.PISTON_BASE;
		} else if (type.equals(Material.PISTON_STICKY_BASE)) {
			checkType = Material.PISTON_STICKY_BASE;
		}
		if (checkType != null) {
			int limit = PermUtils.getMaxPerm(p, "NeverLag.LimitPiston.");
			if (limit <= 0) {
				limit = cm.getLimitTilesPistonDefault();
			}
			if (isLimit(e.getBlock().getLocation(), Material.DISPENSER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.getLimitTilesMessage());
			}
		}
	}

	// 限制发射器
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlaceDispenser(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles()) {
			return;
		}
		if (e.getBlock().getType().equals(Material.DISPENSER)) {
			Player p = e.getPlayer();
			if (p.isOp()) {
				return;
			}
			int limit = PermUtils.getMaxPerm(p, "NeverLag.LimitDispenser.");
			if (limit <= 0) {
				limit = cm.getLimitTilesDispenserDefault();
			}
			if (isLimit(e.getBlock().getLocation(), Material.DISPENSER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.getLimitTilesMessage());
			}
		}
	}

	// 限制投掷器
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlaceDropper(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles()) {
			return;
		}
		if (e.getBlock().getType().equals(Material.DROPPER)) {
			Player p = e.getPlayer();
			if (p.isOp()) {
				return;
			}
			int limit = PermUtils.getMaxPerm(p, "NeverLag.LimitDropper.");
			if (limit <= 0) {
				limit = cm.getLimitTilesDropperDefault();
			}
			if (isLimit(e.getBlock().getLocation(), Material.DROPPER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.getLimitTilesMessage());
			}
		}
	}

	// 判断是否密集
	public Boolean isLimit(Location loc, Material type, int limit) {
		int count = 0;
		int[] offset = { -2, -1, 0, 1, 2 };
		World world = loc.getWorld();
		int baseX = loc.getChunk().getX();
		int baseZ = loc.getChunk().getZ();
		// 获取周围的区块
		HashSet<Chunk> chunksAroundBlock = new HashSet<>();
		for (int x : offset) {
			for (int z : offset) {
				Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
				if (chunk.isLoaded()) {
					chunksAroundBlock.add(chunk);
				}
			}
		}
		// 计算这个类型的Tiles数量
		for (Chunk chunk : chunksAroundBlock) {
			for (BlockState tiles : chunk.getTileEntities()) {
				if (tiles.getBlock().getType().equals(type)) {
					count++;
				}
			}
		}
		if (count >= limit) {
			return true;
		}
		return false;
	}
}
