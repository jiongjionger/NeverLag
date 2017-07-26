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
import org.bukkit.entity.Snowball;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.EntityUtils;
import java.util.LinkedList;
import java.util.List;

public class ItemCleaner {

	private static ConfigManager cm = ConfigManager.getInstance();

	@SuppressWarnings("deprecation")
	public static void doClean() {
		if (!cm.isClearDropItem()) {
			return;
		}
		List<Entity> cleanList = new LinkedList<>();
		for (World world : Bukkit.getWorlds()) {
			// 如果当前世界不在排除列表
			if (!cm.getNoClearItemWorld().contains(world.getName())) {
				for (Entity entity : world.getEntities()) {
					if (entity instanceof Item && cm.isClearItem()) {
						Item item = (Item) entity;
						// 判断是否不在清理的物品ID白名单中
						if (!cm.getNoClearItemId().contains(item.getItemStack().getTypeId())) {
							// 玩家附近的时候是否还清理
							if (!cm.isClearItemPlayerNearby() && EntityUtils.hasPlayerNearby(item, cm.getClearItemPlayerNearbyDistance())) {
								continue;
							}
						} else {
							continue;
						}
					} else if (entity instanceof ItemFrame && cm.isClearItemFrame()) {
					} else if (entity instanceof Boat && cm.isClearBoat()) {
					} else if (entity instanceof ExperienceOrb && cm.isClearExpBall()) {
					} else if (entity instanceof FallingBlock && cm.isClearFallingBlock()) {
					} else if (entity instanceof Painting && cm.isClearPainting()) {
					} else if (entity instanceof Minecart && cm.isClearMinecart()) {
					} else if (entity instanceof Arrow && cm.isClearArrow()) {
					} else if (entity instanceof Snowball && cm.isClearSnowBall()) {
					} else {
						continue;
					}
					cleanList.add(entity);
				}
			}
		}
		for(Entity entity : cleanList) {
			entity.remove();
		}
		if (cm.isBroadcastClearItem()) {
			Bukkit.getServer().broadcastMessage(cm.getClearItemBroadcastMessage().replace("%COUNT%", String.valueOf(cleanList.size())));
		}
	}

	private int preMessageTime = 0;

	private int holoTime = 0;

	public ItemCleaner() {
		NeverLag.getInstance().getServer().getScheduler().runTaskTimer(NeverLag.getInstance(), new Runnable() {
			@Override
			public void run() {
				doClean();
			}
		}, cm.getClearItemDelay() * 20L, cm.getClearItemDelay() * 20L);
		if (cm.getClearItemDelay() > 60) {
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
		if (cm.isClearDropItem() && cm.isBroadcastClearItem()) {
			this.preMessageTime++;
			int remainSecond = cm.getClearItemDelay() - this.preMessageTime;
			if(remainSecond == 60 || remainSecond == 30 || remainSecond == 10) {
				Bukkit.getServer().broadcastMessage(cm.getClearItemBroadcastPreMessage().replace("%TIME%", String.valueOf(remainSecond)));
			}
			if (remainSecond <= 0) {
				this.preMessageTime = 0;
			}
		}
	}

	// 悬浮提醒
	private void holoDisplay() {
		if (!cm.isClearDropItem() || !cm.isClearItem() || !cm.isClearItemPreHoloMessage()) {
			return;
		}
		this.holoTime++;
		int remainSecond = cm.getClearMobDelay() - holoTime;
		if (remainSecond <= 60 && remainSecond > 0) {
			String holoMessage = cm.getClearItemPreHoloMessage().replace("%TIME%", String.valueOf(remainSecond));
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
			if (!cm.getNoClearItemWorld().contains(world.getName())) {
				for (Entity entity : world.getEntities()) {
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
}
