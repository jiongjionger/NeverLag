package cn.jiongjionger.neverlag.cleaner;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Villager;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.NeverLag;

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
					if (entity.hasMetadata("NPC") || entity.hasMetadata("MyPet")) {
						continue;
					}
					if (entity.getNearbyEntities(2.25D, 4.5D, 2.25D).size() >= cm.getCheckMobFarmLooseLimit() || entity.getNearbyEntities(0.50D, 3.5D, 0.5D).size() >= cm.getCheckMobFarmTinyLimit()) {
						entity.remove();
					}
				}
			}
		}
	}
}
