package cn.jiongjionger.neverlag.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;

public final class NeverLagUtils {
	public static int getMaxPermission(Player p, String node) {
		int maxLimit = 0;
		for (PermissionAttachmentInfo perm : p.getEffectivePermissions()) {
			String permission = perm.getPermission();
			if (!permission.toLowerCase().startsWith(node.toLowerCase())) {
				continue;
			}
			String[] split = permission.split("\\.");
			try {
				int number = Integer.parseInt(split[split.length - 1]);
				if (number > maxLimit) {
					maxLimit = number;
				}
			} catch (NumberFormatException ignore) { }
		}
		return maxLimit;
	}

	public static LinkedHashMap<String, Integer> sortMapByValues(HashMap<String, Integer> map) {
		if (map.isEmpty()) {
			return null;
		}
		Set<Map.Entry<String, Integer>> entries = map.entrySet();
		List<Map.Entry<String, Integer>> list = new LinkedList<>(entries);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		LinkedHashMap<String, Integer> sortMap = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> entry : list) {
			sortMap.put(entry.getKey(), entry.getValue());
		}
		return sortMap;
	}

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

	public static Locale toLocale(String str) {
		String language;
		String country = "";

		int idx = str.indexOf('_');
		if (idx == -1) {
			idx = str.indexOf('-');
		}
		if (idx == -1) {
			language = str;
		} else {
			language = str.substring(0, idx);
			country = str.substring(idx + 1, str.length());
		}

		return new Locale(language, country);
	}

	private NeverLagUtils() {}
}
