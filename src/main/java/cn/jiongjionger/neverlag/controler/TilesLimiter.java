package cn.jiongjionger.neverlag.controler;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.PermissionUtils;
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

import java.util.HashSet;

public class TilesLimiter implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	// 判断是否密集
	public boolean isLimit(Location loc, Material type, int limit) {
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
				if (tiles.getBlock().getType() == type) {
					count++;
				}
			}
		}
		return count >= limit;
	}

	// 限制发射器
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlaceDispenser(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles) {
			return;
		}
		if (e.getBlock().getType() == Material.DISPENSER) {
			Player p = e.getPlayer();
			if (p.isOp()) {
				return;
			}
			int limit = PermissionUtils.getMaxPermission(p, "neverlag.limit.dispenser.");
			if (limit <= 0) {
				limit = cm.limitTilesDispenserDefault;
			}
			if (isLimit(e.getBlock().getLocation(), Material.DISPENSER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.limitTilesMessage);
			}
		}
	}

	// 限制投掷器
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlaceDropper(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles) {
			return;
		}
		if (e.getBlock().getType() == Material.DROPPER) {
			Player p = e.getPlayer();
			if (p.isOp()) {
				return;
			}
			int limit = PermissionUtils.getMaxPermission(p, "neverlag.limit.dropper.");
			if (limit <= 0) {
				limit = cm.limitTilesDropperDefault;
			}
			if (isLimit(e.getBlock().getLocation(), Material.DROPPER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.limitTilesMessage);
			}
		}
	}

	// 限制漏斗
	// 将优先级设为NORMAL以与各种小游戏插件兼容. LOWEST可能破坏一些小游戏的游戏机制
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlaceHopper(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles) {
			return;
		}
		if (e.getBlock().getType() == Material.HOPPER) {
			Player p = e.getPlayer();
			if (p.isOp()) {
				return;
			}
			int limit = PermissionUtils.getMaxPermission(p, "neverlag.limit.hopper.");
			if (limit <= 0) {
				limit = cm.limitTilesHopperDefault;
			}
			if (isLimit(e.getBlock().getLocation(), Material.HOPPER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.limitTilesMessage);
			}
		}
	}

	// 限制活塞
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlacePistion(final BlockPlaceEvent e) {
		if (!cm.isLimitTiles) {
			return;
		}
		Player p = e.getPlayer();
		if (p.isOp()) {
			return;
		}
		Material type = e.getBlock().getType();
		Material checkType = null;
		if (type == Material.PISTON_BASE) {
			checkType = Material.PISTON_BASE;
		} else if (type == Material.PISTON_STICKY_BASE) {
			checkType = Material.PISTON_STICKY_BASE;
		}
		if (checkType != null) {
			int limit = PermissionUtils.getMaxPermission(p, "neverlag.limit.piston.");
			if (limit <= 0) {
				limit = cm.limitTilesPistonDefault;
			}
			if (isLimit(e.getBlock().getLocation(), Material.DISPENSER, limit)) {
				e.setCancelled(true);
				p.sendMessage(cm.limitTilesMessage);
			}
		}
	}
}
