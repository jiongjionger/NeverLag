package cn.jiongjionger.neverlag.cleaner;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.utils.EntityUtils;

public class ItemCleaner {

	private static ConfigManager cm = ConfigManager.getInstance();
	private int preMessageTime = 0;
	private int holoTime = 0;

	public ItemCleaner() {
		NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
			public void run() {
				doClean();
			}
		}, cm.getClearItemDelay() * 20L, cm.getClearItemDelay() * 20L);
		if (cm.getClearItemDelay() > 60) {
			NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
				public void run() {
					doPreMessage();
					holoDisplay();
				}
			}, 20L, 20L);
		}
	}

	// 悬浮提醒
	private void holoDisplay() {
		if (!cm.isClearDropItem() || !cm.isClearItem() || !cm.isClearItemPreHoloMessage()) {
			return;
		}
		this.holoTime++;
		int remainTick = cm.getClearMobDelay() - holoTime;
		if (remainTick <= 60 && remainTick > 0) {
			String holoMessage = cm.getClearItemPreHoloMessage().replace("%TIME%", String.valueOf(remainTick));
			this.setDropItemHolo(holoMessage);
		}
		if (remainTick <= 0) {
			this.holoTime = 0;
			this.setDropItemHolo("");
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
			if (!cm.getNoClearItemWorld().contains(world.getName())) {
				for (Entity entity : world.getEntities()) {
					if (entity == null) {
						continue;
					}
					if (entity instanceof Item) {
						Item item = (Item) entity;
						// 判断是否在不清理的物品ID白名单
						if (!cm.getNoClearItemId().contains(item.getItemStack().getTypeId())) {
							item.setCustomName(name);
							item.setCustomNameVisible(true);
						}
					}
				}
			}
		}
	}

	// 提前通知
	private void doPreMessage() {
		if (cm.isClearDropItem() && cm.isBroadcastClearItem()) {
			this.preMessageTime++;
			int remainTick = cm.getClearItemDelay() - this.preMessageTime;
			switch (remainTick) {
			case 60:
				Bukkit.getServer().broadcastMessage(cm.getClearItemBroadcastPreMessage().replace("%TIME%", "60"));
				break;
			case 30:
				Bukkit.getServer().broadcastMessage(cm.getClearItemBroadcastPreMessage().replace("%TIME%", "30"));
				break;
			case 10:
				Bukkit.getServer().broadcastMessage(cm.getClearItemBroadcastPreMessage().replace("%TIME%", "10"));
				break;
			default:
				break;
			}
			if (remainTick <= 0) {
				this.preMessageTime = 0;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void doClean() {
		if (!cm.isClearDropItem()) {
			return;
		}
		int count = 0;
		for (World world : Bukkit.getWorlds()) {
			// 如果当前世界不在排除列表
			if (!cm.getNoClearItemWorld().contains(world.getName())) {
				for (Entity entity : world.getEntities()) {
					if (entity == null) {
						continue;
					}
					if (entity instanceof Item && cm.isClearItem()) {
						Item item = (Item) entity;
						// 判断是否在不清理的物品ID白名单
						if (!cm.getNoClearItemId().contains(item.getItemStack().getTypeId())) {
							// 玩家附近的时候是否还清理
							if (!cm.isClearItemPlayerNearby() && hasPlayerNearby(item, cm.getClearItemPlayerNearbyDistance())) {
								continue;
							}
							entity.remove();
							count++;
						}
					} else if (entity instanceof ItemFrame && cm.isClearItemFrame()) {
						entity.remove();
						count++;
					} else if (entity instanceof Boat && cm.isClearBoat()) {
						entity.remove();
						count++;
					} else if (entity instanceof ExperienceOrb && cm.isClearExpBall()) {
						entity.remove();
						count++;
					} else if (entity instanceof FallingBlock && cm.isClearFallingBlock()) {
						entity.remove();
						count++;
					} else if (entity instanceof Painting && cm.isClearPainting()) {
						entity.remove();
						count++;
					} else if (entity instanceof Minecart && cm.isClearMinecart()) {
						entity.remove();
						count++;
					} else if (entity instanceof Arrow && cm.isClearArrow()) {
						entity.remove();
						count++;
					} else if (entity instanceof Snowball && cm.isClearSnowBall()) {
						entity.remove();
						count++;
					}
				}
			}
		}
		// 公告
		if (cm.isBroadcastClearItem()) {
			Bukkit.getServer().broadcastMessage(cm.getClearItemBroadcastMessage().replace("%COUNT%", String.valueOf(count)));
		}
	}

	/*
	 * 判断掉落物附近有没有玩家
	 * 
	 * @param item 掉落物
	 * 
	 * @param distance 判断距离
	 * 
	 * @return 是否存在玩家
	 */
	private static boolean hasPlayerNearby(Item item, int distance) {
		for (Entity entity : item.getNearbyEntities(distance, distance, distance)) {
			if (entity instanceof Player) {
				if (!EntityUtils.checkCustomNpc(entity)) {
					return true;
				}
			}
		}
		return false;
	}
}
