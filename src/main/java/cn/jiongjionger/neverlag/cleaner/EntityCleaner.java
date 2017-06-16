package cn.jiongjionger.neverlag.cleaner;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.NeverLag;

public class EntityCleaner {

	private static ConfigManager cm = ConfigManager.getInstance();
	private int preMessageTime = 0;

	public EntityCleaner() {
		NeverLag.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(NeverLag.getInstance(), new Runnable() {
			public void run() {
				doClean();
			}
		}, cm.getClearMobDelay() * 20L, cm.getClearMobDelay() * 20L);
		if (cm.getClearMobDelay() > 60) {
			NeverLag.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(NeverLag.getInstance(), new Runnable() {
				public void run() {
					doPreMessage();
				}
			}, 20L, 20L);
		}
	}

	// 提前通知
	private void doPreMessage() {
		if (cm.isClearEntity() && cm.isBroadcastClearEntity()) {
			this.preMessageTime++;
			int remainTick = cm.getClearMobDelay() - this.preMessageTime;
			switch (remainTick) {
			case 60:
				Bukkit.getServer().broadcastMessage(cm.getClearEntityBroadcastPreMessage().replace("%TIME%", "60"));
				break;
			case 30:
				Bukkit.getServer().broadcastMessage(cm.getClearEntityBroadcastPreMessage().replace("%TIME%", "30"));
				break;
			case 10:
				Bukkit.getServer().broadcastMessage(cm.getClearEntityBroadcastPreMessage().replace("%TIME%", "10"));
				break;
			default:
				break;
			}
			if (remainTick <= 0) {
				this.preMessageTime = 0;
			}
		}
	}

	// 外部调用实体清理任务
	public static void doClean() {
		doClean(true);
	}

	// 清理实体任务
	@SuppressWarnings("deprecation")
	private static void doClean(Boolean forceclean) {
		if (!cm.isClearEntity()) {
			return;
		}
		if (!forceclean) {
			if (cm.isClearLimit()) {
				int num = 0;
				for (World world : Bukkit.getWorlds()) {
					num += world.getLivingEntities().size();
				}
				// 如果没到阀值直接返回
				if (num < cm.getClearEntityLimit()) {
					return;
				}
			}
		}
		int count = 0;
		// 循环世界
		for (World world : Bukkit.getWorlds()) {
			if (!cm.getNoClearEntityWorld().contains(world.getName())) {
				for (LivingEntity entity : world.getLivingEntities()) {
					// 不清理NPC和Mypet宠物和白名单内的类型
					if (entity.hasMetadata("NPC") || entity.hasMetadata("MyPet") || cm.getClearEntityTypeWhiteList().contains(entity.getType().getName().toLowerCase())) {
						continue;
					}
					if (!cm.isClearEntityPlayerNearby() && hasPlayerNearby(entity, cm.getClearEntityPlayerNearbyDistance())) {
						continue;
					}
					if (entity instanceof Animals) {
						entity.remove();
						count++;
					} else if (entity instanceof Monster) {
						entity.remove();
						count++;
					} else if (entity instanceof Squid) {
						entity.remove();
						count++;
					} else if (entity instanceof Villager) {
						entity.remove();
						count++;
					} else if (cm.getClearEntityTypeBlackList().contains(entity.getType().getName().toLowerCase())) {
						entity.remove();
						count++;
					}
				}
			}
		}
		if (cm.isBroadcastClearEntity()) {
			Bukkit.getServer().broadcastMessage(cm.getClearEntityBroadcastMessage().replace("%COUNT%", String.valueOf(count)));
		}
	}

	private static boolean hasPlayerNearby(Entity ent, int distance) {
		for (Entity entity : ent.getNearbyEntities(distance, distance, distance)) {
			if (entity instanceof Player) {
				if (!entity.hasMetadata("NPC")) {
					return true;
				}
			}
		}
		return false;
	}
}
