package cn.jiongjionger.neverlag.controler;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import cn.jiongjionger.neverlag.config.ConfigManager;

public class SpawnRater {

	private final Random rnd = new Random();
	private ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void rateControler(CreatureSpawnEvent e) {
		if(!cm.isSpawnRate()){
			return;
		}
		// 获取刷怪原因（来源）
		SpawnReason spawnReason = e.getSpawnReason();
		switch (spawnReason) {
		// 特殊区块
		case CHUNK_GEN:
			if (cm.getChunkGenSpawnRate() <= 0 || rnd.nextInt(100) > cm.getChunkGenSpawnRate()) {
				e.setCancelled(true);
			}
			break;
		// 刷怪笼
		case SPAWNER:
			if (cm.getSpawnerSpawnRate() <= 0 || rnd.nextInt(100) > cm.getSpawnerSpawnRate()) {
				e.setCancelled(true);
			}
			break;
		// 刷铁塔
		case VILLAGE_DEFENSE:
			if (cm.getVillageSpawnRate() <= 0 || rnd.nextInt(100) > cm.getVillageSpawnRate()) {
				e.setCancelled(true);
			}
			break;
		// 自然条件
		case NATURAL:
			if (cm.getNormalSpawnRate() <= 0 || rnd.nextInt(100) > cm.getNormalSpawnRate()) {
				e.setCancelled(true);
			}
			break;
		// 下界传送门
		case NETHER_PORTAL:
			if (cm.getPortalSpawnRate() <= 0 || rnd.nextInt(100) > cm.getPortalSpawnRate()) {
				e.setCancelled(true);
			}
			break;
		default:
			break;
		}
	}
}
