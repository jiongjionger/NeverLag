package cn.jiongjionger.neverlag.controler;

import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.Random;

public class SpawnRater implements Listener {

	private final Random rnd = new Random();
	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void rateControler(CreatureSpawnEvent e) {
		if (!cm.isSpawnRate) {
			return;
		}
		// 获取刷怪原因（来源）
		SpawnReason spawnReason = e.getSpawnReason();
		switch (spawnReason) {
		// 特殊区块
		case CHUNK_GEN:
			if (cm.chunkGenSpawnRate <= 0 || rnd.nextInt(100) > cm.chunkGenSpawnRate) {
				e.setCancelled(true);
			}
			break;
		// 刷怪笼
		case SPAWNER:
			if (cm.spawnerSpawnRate <= 0 || rnd.nextInt(100) > cm.spawnerSpawnRate) {
				e.setCancelled(true);
			}
			break;
		// 刷铁塔
		case VILLAGE_DEFENSE:
			if (cm.villageSpawnRate <= 0 || rnd.nextInt(100) > cm.villageSpawnRate) {
				e.setCancelled(true);
			}
			break;
		// 自然条件
		case NATURAL:
			if (cm.normalSpawnRate <= 0 || rnd.nextInt(100) > cm.normalSpawnRate) {
				e.setCancelled(true);
			}
			break;
		// 下界传送门
		case NETHER_PORTAL:
			if (cm.portalSpawnRate <= 0 || rnd.nextInt(100) > cm.portalSpawnRate) {
				e.setCancelled(true);
			}
			break;
		}
	}
}
