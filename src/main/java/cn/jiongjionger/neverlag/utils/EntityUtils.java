package cn.jiongjionger.neverlag.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class EntityUtils {

	public static boolean checkCustomNpc(Entity entity) {
		return entity == null || entity.hasMetadata("NPC") || entity.hasMetadata("MyPet");
	}
	
	/*
	 * 判断掉落物附近有没有玩家
	 * 
	 * @param item 掉落物
	 * @param distance 判断距离
	 * @return 是否存在玩家
	 */
	public static boolean hasPlayerNearby(Item item, int distance) {
		for (Entity entity : item.getNearbyEntities(distance, distance, distance)) {
			if (entity instanceof Player && !checkCustomNpc(entity)) {
				return true;
			}
		}
		return false;
	}

	// 实用程序类封闭构造器
	private EntityUtils() throws AssertionError {
		throw new AssertionError();
	}
}
