package cn.jiongjionger.neverlag.cleaner;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.NeverLagUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({ "RedundantIfStatement", "SimplifiableIfStatement" })
public class EntityCleaner implements Runnable {
	private final ConfigManager cm = ConfigManager.getInstance();
	private final I18n i18n = NeverLag.i18n("cleaner");

	public EntityCleaner() {
		// TODO: 重构调度机制
		Bukkit.getScheduler().runTaskTimer(NeverLag.getInstance(), this, 20L, 20L);
	}

	private int itemTimeCounter = 0;
	private int entityTimeCounter = 0;

	@Override
	public void run() {
		if (cm.cleanItemEnabled) tickItem();
		if (cm.cleanEntityEnabled) tickEntity();
	}

	private void tickItem() {
		itemTimeCounter++;

		if (cm.cleanItemInterval >= itemTimeCounter) {
			// 计时器归零操作由 clean 方法执行
			clean(true, false);
		} else {
			int remain = cm.cleanItemInterval - itemTimeCounter;
			if (cm.cleanItemForenotice) {
				if (remain == 60 || remain == 30 || remain == 10) {   // TODO: 低优先级: 看看能不能让其可配置
					NeverLagUtils.broadcastIfOnline(i18n.tr("item.forenotice", remain));
				}
			}

			if (cm.cleanItemHoloMessage) {
				updateHoloMessage(remain);
			}
		}
	}

	private void tickEntity() {
		entityTimeCounter++;

		if (cm.cleanEntityInterval >= entityTimeCounter) {
			// 计时器归零操作由 clean 方法执行
			clean(false, true);
		}
	}

	public void clean(boolean cleanItem, boolean cleanEntity) { // TODO: 支持清理阈值与 forceclean
		if (cleanItem) itemTimeCounter = 0;
		if (cleanEntity) entityTimeCounter = 0;

		int itemCount = 0, mobCount = 0, entityCount = 0;
		for (World world : Bukkit.getWorlds()) {
			String worldName = world.getName();
			if ((!cleanItem || cm.cleanItemWorldWhitelist.contains(worldName)) &&
				(!cleanEntity || cm.cleanEntityWorldWhitelist.contains(worldName))) {
				continue; // 如果这个世界既不允许清理掉落物也不允许清理实体就不浪费时间了
			}

			for (Entity entity : world.getEntities()) {
				if (entity instanceof Item) {
					// 世界白名单之前检查过了, 没必要再次检查
					if (canCleanItem((Item) entity, false)) {
						itemCount++;
					}
				} else {
					if (canClean(entity)) {
						entity.remove();

						if (entity instanceof LivingEntity) {
							mobCount++;
						} else {
							entityCount++;
						}
					}
				}
			}
		}

		if (cm.cleanItemBroadcast && itemCount > 0) {
			NeverLagUtils.broadcastIfOnline(i18n.tr("item.broadcast", itemCount));
		}

		if (cm.cleanEntityBroadcast) {
			if (mobCount > 0) {
				NeverLagUtils.broadcastIfOnline(i18n.tr("mob", mobCount));
			}
			if (entityCount > 0) {
				NeverLagUtils.broadcastIfOnline(i18n.tr("entity", entityCount));
			}
		}
	}

	/**
	 * 判断非掉落物的实体能否被清理.
	 * <p>
	 * 按以下顺序依次判定, 一旦有一条满足则跳过剩下的步骤:
	 * <ol>
	 * <li>如果为掉落物: 抛异常</li>
	 * <li>不是掉落物, 并且实体清理未开启: 不清理</li>
	 * <li>所在世界在白名单中: 不清理</li>
	 * <li>距离判定开启并符合条件: 不清理</li>
	 * <li>实体类型在排除列表中: 不清理</li>
	 * <li>实体类型在包含列表中: 清理</li>
	 * <li>
	 * 是{@linkplain LivingEntity 生物}:
	 * <ol>
	 * <li>是动物且清理动物: 清理</li>
	 * <li>是怪物且清理怪物: 清理</li>
	 * <li>是鱿鱼且清理鱿鱼: 清理</li>
	 * <li>否则: 不清理</li>
	 * </ol>
	 * </li>
	 * <li>是{@linkplain Projectile 弹射物}: 如果清理弹射物则清理</li>
	 * <li>否则: 不清理</li>
	 * </ol>
	 */
	public boolean canClean(Entity entity) {
		if (entity instanceof Item) {
			throw new AssertionError("此方法不能判定掉落物");
		}

		if (!cm.cleanEntityEnabled) {
			return false; // 除了掉落物外的所有实体都按实体对待
		}

		if (cm.cleanEntityWorldWhitelist.contains(entity.getWorld().getName())) {
			return false;
		}

		if (cm.cleanEntityPlayerNearbyDistance > 0 &&
			NeverLagUtils.hasPlayerNearby(entity, cm.cleanEntityPlayerNearbyDistance)) {
			return false;
		}

		String type = entity.getType().toString();
		if (cm.cleanEntityExcludeList.contains(type)) {
			return false;
		}

		if (cm.cleanEntityIncludeList.contains(type)) {
			return true;
		}

		if (entity instanceof LivingEntity) {
			return canCleanLivingEntity(((LivingEntity) entity));
		}

		if (entity instanceof Projectile) {
			return canCleanProjectile(((Projectile) entity));
		}

		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean canCleanItem(Item item, boolean checkWorldWhitelist) {
		if (!cm.cleanItemEnabled) {
			return false;
		}
		if (checkWorldWhitelist && cm.cleanItemWorldWhitelist.contains(item.getWorld().getName())) {
			return false;
		}
		if (cm.cleanItemIdWhitelist.contains(item.getItemStack().getTypeId())) {
			return false;
		}
		if (cm.cleanItemPlayerNearbyDistance > 0 &&
			NeverLagUtils.hasPlayerNearby(item, cm.cleanItemPlayerNearbyDistance)) {
			return false;
		}

		return true;
	}

	public boolean canCleanLivingEntity(LivingEntity entity) {
		if (entity instanceof Animals && cm.cleanEntityIncludeAnimals) {
			return true;
		}

		if (entity instanceof Monster && cm.cleanEntityIncludeMonsters) {
			return true;
		}

		if (entity instanceof Squid && cm.cleanEntityIncludeSquids) {
			return true;
		}

		return false;
	}

	public boolean canCleanProjectile(Projectile projectile) {
		return cm.cleanEntityIncludeProjectiles;
	}

	private final Set<Item> holoMessage = new HashSet<>();

	/**
	 * @param remain 小于 0 则为清除
	 */
	private void updateHoloMessage(int remain) {
		String message = remain > 0 ? i18n.tr("item.holoTitle", remain) : null;
		for (World world : Bukkit.getWorlds()) {
			if (cm.cleanItemWorldWhitelist.contains(world.getName())) {
				continue;
			}

			for (Item item : world.getEntitiesByClass(Item.class)) {
				// 当前是否已经在显示倒计时
				boolean displaying = holoMessage.contains(item);

				if (canCleanItem(item, false)) {
					/*
						只有满足了如下条件之一才显示倒计时:
						1. 已在显示倒计时的
						2. custom name 未被设置的 (与其他插件兼容)
					*/
					if (displaying || (item.getCustomName() == null || !item.isCustomNameVisible())) {
						item.setCustomName(message);
						item.setCustomNameVisible(message != null);

						// 更新记录
						if (message != null) {
							if (!displaying) holoMessage.add(item);
						} else {
							if (displaying) holoMessage.remove(item);
						}
					}
				} else if (displaying) { // 如果之前在显示倒计时, 但现在不再满足显示条件了
					item.setCustomName(null);
					holoMessage.remove(item);
				}
			}
		}
	}
}
