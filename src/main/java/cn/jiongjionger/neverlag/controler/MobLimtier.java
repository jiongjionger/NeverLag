package cn.jiongjionger.neverlag.controler;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.NeverLag;

public class MobLimtier implements Listener {

	private ConfigManager cm = ConfigManager.getInstance();
	private int cachedMobCount = 0;
	private long lastCountTime = System.currentTimeMillis();

	private void countTotalEntity() {
		int count = 0;
		for (World world : Bukkit.getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				if (entity instanceof Monster || entity instanceof Animals || entity instanceof Villager || entity.getType() == EntityType.SQUID || entity.getType() == EntityType.BAT) {
					count++;
				}
			}
		}
		this.cachedMobCount = count;
		this.lastCountTime = System.currentTimeMillis();
	}

	// 5秒统计一次实体数量，而不是每次调用都计算，节约开销
	private int getMobCount() {
		if (System.currentTimeMillis() - lastCountTime >= 5000L || cachedMobCount == 0) {
			this.countTotalEntity();
		}
		return this.cachedMobCount;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if (!cm.isLimitEntitySpawn()) {
			return;
		}
		// 如果开启了TPS阀值，当TPS大于阀值，就不限制
		if (cm.isLimitEntitySpawnByTps() && NeverLag.getTpsWatcher().getAverageTPS() >= cm.getEntitySpawnLimitTpsLimit()) {
			return;
		}
		// 实体有自定义名字的不限制，兼容MM怪物等插件
		LivingEntity creature = e.getEntity();
		if (creature.getCustomName() != null) {
			return;
		}
		// 不限制插件、刷怪蛋、刷怪笼刷出的实体和凋零、铁傀儡、雪人
		if (creature.getType() != EntityType.IRON_GOLEM && creature.getType() != EntityType.SNOWMAN && creature.getType() != EntityType.WITHER
				&& e.getSpawnReason() != SpawnReason.SPAWNER_EGG && e.getSpawnReason() != SpawnReason.SPAWNER
				&& e.getSpawnReason() != SpawnReason.CUSTOM) {
			if (creature instanceof Animals) {
				if (this.getMobCount() >= cm.getAnimalsSpawnLimit()) {
					e.setCancelled(true);
				}
			} else if (creature instanceof Monster) {
				if (this.getMobCount() >= cm.getMobSpawnLimit()) {
					e.setCancelled(true);
				}
			}
		}
		// 刷怪笼不按照总数量限制，而是按照区块内实体数量
		if (e.getSpawnReason().equals(SpawnReason.SPAWNER)) {
			int count = 0;
			for (Entity entity : e.getLocation().getChunk().getEntities()) {
				if (creature instanceof Monster && entity instanceof Monster) {
					count++;
				} else if (creature instanceof Animals && entity instanceof Animals) {
					count++;
				} else if (entity instanceof Monster) {
					count++;
				}
				if (count > cm.getSpawnerEntityCountPerChunkLimit()) {
					e.setCancelled(true);
					break;
				}
			}
		}
	}
}
