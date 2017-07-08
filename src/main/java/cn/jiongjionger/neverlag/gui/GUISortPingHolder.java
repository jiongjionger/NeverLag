package cn.jiongjionger.neverlag.gui;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class GUISortPingHolder {

	private static HashMap<String, GUISortPing> record = new HashMap<>();

	public static void clear(Player p) {
		record.remove(p.getName());
	}

	public static void clear(String username) {
		record.remove(username);
	}

	public static GUISortPing get(Player p) {
		return get(p.getName());
	}

	public static GUISortPing get(String username) {
		return record.get(username);
	}

	public static void put(Player p, GUISortPing sortPing) {
		put(p.getName(), sortPing);
	}

	public static void put(String username, GUISortPing sortPing) {
		record.put(username, sortPing);
	}

}
