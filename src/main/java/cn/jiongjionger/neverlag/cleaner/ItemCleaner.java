package cn.jiongjionger.neverlag.cleaner;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.NeverLagUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

public class ItemCleaner {

	private static final ConfigManager cm = ConfigManager.getInstance();

	@SuppressWarnings("deprecation")
	public static void doClean() {
		if (!cm.isClearDropItem) {
			return;
		}
		int count = 0;
		for (World world : Bukkit.getWorlds()) {
			// 如果当前世界不在排除列表
			if (!cm.noClearItemWorld.contains(world.getName())) {
				for (Entity entity : world.getEntities()) {
					if (entity instanceof Item && cm.isClearItem) {
						Item item = (Item) entity;
						if (!canClean(item)) {
							continue;
						}
					} else if (entity instanceof ItemFrame && cm.isClearItemFrame) {
					} else if (entity instanceof Boat && cm.isClearBoat) {
					} else if (entity instanceof ExperienceOrb && cm.isClearExpBall) {
					} else if (entity instanceof FallingBlock && cm.isClearFallingBlock) {
					} else if (entity instanceof Painting && cm.isClearPainting) {
					} else if (entity instanceof Minecart && cm.isClearMinecart) {
					} else if (entity instanceof Arrow && cm.isClearArrow) {
					} else if (entity instanceof Snowball && cm.isClearSnowBall) {
					} else {
						continue;
					}
					entity.remove();
					count++;
				}
			}
		}
		if (cm.isBroadcastClearItem && count > 0) {
			NeverLagUtils.broadcastIfOnline(cm.clearItemBroadcastMessage.replace("%COUNT%", String.valueOf(count)));
		}
	}

	@SuppressWarnings("RedundantIfStatement")
	private static boolean canClean(Item item) {
		// 判断是否不在清理的物品ID白名单中
		if (cm.noClearItemId.contains(item.getItemStack().getTypeId())) {
			return false;
		}

		// 玩家在附近时是否还清理
		if (!cm.isClearItemPlayerNearby && NeverLagUtils.hasPlayerNearby(item, cm.clearItemPlayerNearbyDistance)) {
			return false;
		}

		return true;
	}

	private int preMessageTime = 0;
	private int holoTime = 0;

	public ItemCleaner() {
		NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
			@Override
			public void run() {
				doClean();
			}
		}, cm.clearItemDelay * 20L, cm.clearItemDelay * 20L);
		if (cm.clearItemDelay > 60) {
			NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
				@Override
				public void run() {
					doPreMessage();
					holoDisplay();
				}
			}, 20L, 20L);
		}
	}

	// 提前通知
	private void doPreMessage() {
		if (cm.isClearDropItem && cm.isBroadcastClearItem) {
			this.preMessageTime++;
			int remainSecond = cm.clearItemDelay - this.preMessageTime;
			if (remainSecond == 60 || remainSecond == 30 || remainSecond == 10) {
				NeverLagUtils.broadcastIfOnline(cm.clearItemBroadcastPreMessage.replace("%TIME%", String.valueOf(remainSecond)));
			}
			if (remainSecond <= 0) {
				this.preMessageTime = 0;
			}
		}
	}

	// 悬浮提醒
	private void holoDisplay() {
		if (!cm.isClearDropItem || !cm.isClearItem || !cm.isClearItemPreHoloMessage) {
			return;
		}
		this.holoTime++;
		int remainSecond = cm.clearMobDelay - holoTime;
		if (remainSecond <= 60 && remainSecond > 0) {
			String holoMessage = cm.clearItemPreHoloMessage.replace("%TIME%", String.valueOf(remainSecond));
			this.setDropItemHolo(holoMessage);
		}
		if (remainSecond <= 0) {
			this.holoTime = 0;
			this.setDropItemHolo(null);
		}
	}

	/*
	 * 设置掉落物的自定义名字和显示以实现悬浮显示效果
	 * 
	 * @param name 自定义显示的名字（倒计时）
	 */
	@SuppressWarnings("deprecation")
	private void setDropItemHolo(String name) {
		for (World world : Bukkit.getWorlds()) {
			// 如果当前世界不在排除列表
			if (!cm.noClearItemWorld.contains(world.getName())) {
				for (Entity entity : world.getEntities()) {
					if (entity instanceof Item) {
						Item item = (Item) entity;
						if (canClean(item)) {
							item.setCustomName(name);
							item.setCustomNameVisible(true);
						}
					}
				}
			}
		}
	}
}
