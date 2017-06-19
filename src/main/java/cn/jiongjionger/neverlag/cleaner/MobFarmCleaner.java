package cn.jiongjionger.neverlag.cleaner;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Villager;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.utils.EntityUtils;

public class MobFarmCleaner {

	private NeverLag plg = NeverLag.getInstance();
	private ConfigManager cm = ConfigManager.getInstance();

	public MobFarmCleaner() {
		plg.getServer().getScheduler().runTaskTimer(plg, new Runnable() {
			public void run() {
				if (cm.isCheckMobFarm()) {
					checkAndCleanMobFarm();
				}
			}
		}, cm.getCheckMobFarmDelay() * 20L, cm.getCheckMobFarmDelay() * 20L);
	}

	// 清理密集实体
	private void checkAndCleanMobFarm() {
		for (World world : Bukkit.getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				if (entity instanceof Monster || entity instanceof Animals || entity instanceof Villager || entity.getType() == EntityType.SQUID) {
					if (EntityUtils.checkCustomNpc(entity)) {
						continue;
					}
					if (this.getNearbyEntityCount(entity, false) >= cm.getCheckMobFarmLooseLimit() || this.getNearbyEntityCount(entity, true) >= cm.getCheckMobFarmTinyLimit()) {
						entity.remove();
					}
				}
			}
		}
	}

	/*
	 * 统计实体附近特定实体类型的数量
	 * 
	 * @param isTiny 是否为狭窄检测模式
	 * 
	 * @return 附近实体数量
	 */
	private int getNearbyEntityCount(LivingEntity entity, boolean isTiny) {
		List<Entity> entityList = new ArrayList<Entity>();
		if (isTiny) {
			entityList = entity.getNearbyEntities(0.50D, 3.5D, 0.5D);
		} else {
			entityList = entity.getNearbyEntities(2.25D, 4.5D, 2.25D);
		}
		int count = 0;
		for (Entity ent : entityList) {
			if (ent instanceof Monster || ent instanceof Animals || ent instanceof Villager || ent.getType() == EntityType.SQUID) {
				count++;
			}
		}
		return count;
	}
}
