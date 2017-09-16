package cn.jiongjionger.neverlag.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class EntityUtils {

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
	public static boolean hasPlayerNearby(Entity entity, int distance) {
		for (Entity e : entity.getNearbyEntities(distance, distance, distance)) {
			if (e instanceof Player && !checkCustomNpc(e)) {
				return true;
			}
		}
		return false;
	}

	private EntityUtils() {}
}
