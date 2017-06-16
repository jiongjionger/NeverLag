package cn.jiongjionger.neverlag.cleaner;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

@SuppressWarnings("deprecation")
public class AutoCleanIllegalTypeSpawner implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final CreatureType[] RND_ENTITYTYPE = { CreatureType.CAVE_SPIDER, CreatureType.SPIDER, CreatureType.ZOMBIE, CreatureType.SKELETON };

	@EventHandler(priority = EventPriority.LOWEST)
	private void onChunkLoad(ChunkLoadEvent e) {
		if (cm.isAutoCleanIllegalTypeSpawner()) {
			this.autoClean(e.getChunk());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onChunkUnLoad(ChunkUnloadEvent e) {
		if (cm.isAutoCleanIllegalTypeSpawner()) {
			this.autoClean(e.getChunk());
		}
	}

	private void autoClean(Chunk chunk) {
		if (chunk == null) {
			return;
		}
		for (BlockState tiles : chunk.getTileEntities()) {
			if (tiles instanceof CreatureSpawner) {
				CreatureSpawner spawner = (CreatureSpawner) tiles;
				// 非法类型检测
				if (cm.getIllegalSpawnerTypeSet().contains(spawner.getCreatureTypeName().toLowerCase())) {
					switch (cm.getIllegalTypeSpawnerCleanMode()) {
					case 0:
						spawner.getBlock().setType(Material.AIR);
						break;
					case 1:
						spawner.setCreatureType(RND_ENTITYTYPE[(int) (Math.random() * RND_ENTITYTYPE.length)]);
						break;
					default:
						break;
					}
				}
			}
		}
	}

}
