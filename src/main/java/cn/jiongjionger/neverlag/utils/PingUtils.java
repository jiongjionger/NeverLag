package cn.jiongjionger.neverlag.utils;

import cn.jiongjionger.neverlag.utils.Reflection.FieldAccessor;
import cn.jiongjionger.neverlag.utils.Reflection.MethodInvoker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;

public final class PingUtils {
	private static final MethodInvoker method_getHandle;
	private static final FieldAccessor<Integer> field_ping;

	static {
		method_getHandle = Reflection.getMethod(Reflection.getCraftBukkitClass("entity.CraftEntity"), "getHandle");
		field_ping = Reflection.getField(Reflection.getMinecraftClass("EntityPlayer"), "ping", int.class);
	}

	// 给延迟标注颜色
	public static String colorPing(int ping) {
		StringBuilder sb = new StringBuilder();
		if (ping <= 80) {
			sb.append(ChatColor.GREEN);
		} else if (ping <= 110) {
			sb.append(ChatColor.DARK_GREEN);
		} else if (ping <= 165) {
			sb.append(ChatColor.YELLOW);
		} else {
			sb.append(ChatColor.RED);
		}
		sb.append(ping).append("ms");
		return sb.toString();
	}

	// 获取玩家网络延迟
	public static int getPing(Player p) {
		return field_ping.get(method_getHandle.invoke(p));
	}

	// 获取全服玩家的延迟并且排序
	public static LinkedHashMap<String, Integer> getPingAndSort() {
		HashMap<String, Integer> pingRecord = new HashMap<>();
		for (World world : Bukkit.getWorlds()) {
			for (Player p : world.getPlayers()) {
				pingRecord.put(p.getName(), getPing(p));
			}
		}
		return NeverLagUtils.sortMapByValues(pingRecord);
	}

	private PingUtils() {}
}
