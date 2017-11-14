package cn.jiongjionger.neverlag.cleaner;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.NeverLagUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

// TODO: 重构 - 把除了 Item 外的判断全部移到 EntityCleaner 去
public class ItemCleaner {

	private static final ConfigManager cm = ConfigManager.getInstance();
	private static final I18n i18n = NeverLag.i18n("cleaner.item");

	@SuppressWarnings("deprecation")
	public static void doClean() {
		if (!cm.cleanItemEnabled) {
			return;
		}
		int count = 0;
		for (World world : Bukkit.getWorlds()) {
			// 如果当前世界不在排除列表
			if (!cm.cleanItemWorldWhitelist.contains(world.getName())) {
				for (Entity entity : world.getEntities()) {
					if (entity instanceof Item && cm.cleanItemEnabled) {
						Item item = (Item) entity;
						if (!canClean(item)) {
							continue;
						}
					} else if (entity instanceof ItemFrame && cm.cleanEntityItemFrame) {
					} else if (entity instanceof Boat && cm.cleanEntityBoat) {
					} else if (entity instanceof ExperienceOrb && cm.cleanEntityExpBall) {
					} else if (entity instanceof FallingBlock && cm.cleanEntityFallingBlock) {
					} else if (entity instanceof Painting && cm.cleanEntityPainting) {
					} else if (entity instanceof Minecart && cm.cleanEntityMinecart) {
					} else if (entity instanceof Arrow && cm.cleanEntityArrow) {
					} else if (entity instanceof Snowball && cm.cleanEntitySnowBall) {
					} else {
						continue;
					}
					entity.remove();
					count++;
				}
			}
		}
		if (cm.cleanItemBroadcast && count > 0) {
			NeverLagUtils.broadcastIfOnline(i18n.tr("broadcast", count));
		}
	}

	@SuppressWarnings("RedundantIfStatement")
	private static boolean canClean(Item item) {
		// 判断是否不在清理的物品ID白名单中
		if (cm.cleanItemIdWhitelist.contains(item.getItemStack().getTypeId())) {
			return false;
		}

		// 玩家在附近时是否还清理
		if (cm.cleanItemPlayerNearbyDistance > 0 && NeverLagUtils.hasPlayerNearby(item, cm.cleanItemPlayerNearbyDistance)) {
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
		}, cm.cleanItemInterval * 20L, cm.cleanItemInterval * 20L);
		if (cm.cleanItemInterval > 60) {
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
		if (cm.cleanItemEnabled && cm.cleanItemForenotice) {
			this.preMessageTime++;
			int remainSecond = cm.cleanItemInterval - this.preMessageTime;
			if (remainSecond == 60 || remainSecond == 30 || remainSecond == 10) {
				NeverLagUtils.broadcastIfOnline(i18n.tr("forenotice", remainSecond));
			}
			if (remainSecond <= 0) {
				this.preMessageTime = 0;
			}
		}
	}

	// 悬浮提醒
	private void holoDisplay() {
		if (!cm.cleanItemEnabled || !cm.cleanItemHoloForenotice) {
			return;
		}
		this.holoTime++;
		int remainSecond = cm.cleanEntityInterval - holoTime;
		if (remainSecond <= 60 && remainSecond > 0) {
			String holoMessage = i18n.tr("holoTitle", remainSecond);
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
			if (!cm.cleanItemWorldWhitelist.contains(world.getName())) {
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
