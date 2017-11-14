package cn.jiongjionger.neverlag.cleaner;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.NeverLagUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

public class EntityCleaner {

	private static final ConfigManager cm = ConfigManager.getInstance();
	private static final I18n i18n = NeverLag.i18n("cleaner.entity");

	// 外部调用实体清理任务
	public static void doClean() {
		doClean(true);
	}

	/*
	 * 清理实体任务
	 * 
	 * @param forceclean 是否强制清理，无视实体总量是否达到阀值
	 */
	@SuppressWarnings("deprecation")
	private static void doClean(boolean forceclean) {
		if (!cm.cleanEntity) {
			return;
		}
		if (!forceclean) {
			if (cm.cleanEntityThreshold > 0) {
				int num = 0;
				for (World world : Bukkit.getWorlds()) {
					num += world.getLivingEntities().size();
				}
				// 如果没到阀值直接返回
				if (num < cm.cleanEntityThreshold) {
					return;
				}
			}
		}
		int count = 0;
		// 循环世界
		for (World world : Bukkit.getWorlds()) {
			if (!cm.cleanEntityWorldWhitelist.contains(world.getName())) {
				for (LivingEntity entity : world.getLivingEntities()) {
					// 不清理NPC和Mypet宠物和白名单内的类型
					if (NeverLagUtils.checkCustomNpc(entity) || cm.clearEntityTypeWhiteList.contains(entity.getType().getName().toLowerCase())) {
						continue;
					}
					if (cm.cleanEntityPlayerNearbyDistance > 0 && NeverLagUtils.hasPlayerNearby(entity, cm.cleanEntityPlayerNearbyDistance)) {
						continue;
					}
					if (entity instanceof Animals) {
					} else if (entity instanceof Monster) {
					} else if (entity instanceof Squid) {
					} else if (entity instanceof Villager) {
					} else if (cm.clearEntityTypeBlackList.contains(entity.getType().getName().toLowerCase())) {
					} else {
						continue;
					}
					entity.remove();
					count++;
				}
			}
		}
		if (cm.cleanEntityBroadcast && count > 0) {
			NeverLagUtils.broadcastIfOnline(i18n.tr("broadcast", count));
		}
	}

	private int preMessageTime = 0;

	public EntityCleaner() {
		NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
			@Override
			public void run() {
				doClean();
			}
		}, cm.cleanEntityInterval * 20L, cm.cleanEntityInterval * 20L);
		if (cm.cleanEntityInterval > 60) {
			NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
				@Override
				public void run() {
					doPreMessage();
				}
			}, 20L, 20L);
		}
	}

	// 提前通知
	private void doPreMessage() {
		if (cm.cleanEntity && cm.cleanEntityBroadcast) {
			this.preMessageTime++;
			int remainSecond = cm.cleanEntityInterval - this.preMessageTime;
			if (remainSecond == 60 || remainSecond == 30 || remainSecond == 10) {
				NeverLagUtils.broadcastIfOnline(i18n.tr("forenotice", remainSecond));
			}
			if (remainSecond <= 0) {
				this.preMessageTime = 0;
			}
		}
	}
}
