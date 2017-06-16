package cn.jiongjionger.neverlag.tweaker;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class RemoveEntityWhenChunkUnload implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onChunkUnload(ChunkUnloadEvent e) {
		if(!cm.isRemoveEntityWhenChunkUnload()){
			return;
		}
		for (Entity entity : e.getChunk().getEntities()) {
			if (entity.hasMetadata("NPC") || entity.hasMetadata("MyPet")) {
				continue;
			}
			if (entity instanceof Monster && cm.isRemoveMonsterWhenChunkUnload()) {
				entity.remove();
			} else if (entity instanceof Animals && cm.isRemoveAnimalsWhenChunkUnload()) {
				entity.remove();
			} else if (entity instanceof Item && cm.isRemoveDropItemWhenChunkUnload()) {
				entity.remove();
			} else if (entity instanceof Arrow && cm.isRemoveArrowWhenChunkUnload()) {
				entity.remove();
			} else if (entity instanceof Squid && cm.isRemoveSquidWhenChunkUnload()) {
				entity.remove();
			}
		}
	}
}
