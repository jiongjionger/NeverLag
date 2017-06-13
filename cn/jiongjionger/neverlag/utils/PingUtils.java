package cn.jiongjionger.neverlag.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;
import cn.jiongjionger.neverlag.utils.Reflection.MethodInvoker;

public class PingUtils {

	private static MethodInvoker method_getHandle = null;
	private static FieldAccessor<Integer> field_ping = null;
	private static boolean isInit = false;
	
	public static void init() {
		try {
			method_getHandle = Reflection.getMethod(Reflection.getCraftBukkitClass("entity.CraftEntity"), "getHandle");
			field_ping = Reflection.getField(Reflection.getMinecraftClass("EntityPlayer"), "ping", int.class);
			isInit = true;
		} catch (Exception e) {
			isInit = false;
		}
	}

	// 获取玩家网络延迟
	public static int getPing(Player p) {
		if (!isInit) {
			return -1;
		}
		return field_ping.get(method_getHandle.invoke(p));
	}

	// 获取全服玩家的延迟并且排序
	public static LinkedHashMap<String, Integer> getPingAndSort() {
		if (!isInit) {
			return null;
		}
		HashMap<String, Integer> pingRecord = new HashMap<String, Integer>();
		for (World world : Bukkit.getWorlds()) {
			for (Player p : world.getPlayers()) {
				pingRecord.put(p.getName(), getPing(p));
			}
		}
		return sortMapByValues(pingRecord);
	}

	private static LinkedHashMap<String, Integer> sortMapByValues(HashMap<String, Integer> map) {
		if (map.isEmpty()) {
			return null;
		}
		Set<Entry<String, Integer>> entries = map.entrySet();
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(entries);
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		LinkedHashMap<String, Integer> sortMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortMap.put(entry.getKey(), entry.getValue());
		}
		return sortMap;
	}

	// 给延迟标注颜色
	public static String colorPing(int ping) {
		StringBuilder sb = new StringBuilder();
		if (ping <= 80) {
			sb.append(ChatColor.GREEN);
		} else if (ping > 80 && ping <= 110) {
			sb.append(ChatColor.DARK_GREEN);
		} else if (ping > 110 && ping <= 165) {
			sb.append(ChatColor.YELLOW);
		} else if (ping > 165) {
			sb.append(ChatColor.RED);
		}
		sb.append(ping).append("ms");
		return sb.toString();
	}
}
