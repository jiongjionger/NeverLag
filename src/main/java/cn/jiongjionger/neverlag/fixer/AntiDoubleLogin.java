package cn.jiongjionger.neverlag.fixer;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiDoubleLogin implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.HIGH)
	private void onLogin(AsyncPlayerPreLoginEvent e) {
		if (!cm.isAntiDoubleLogin()) {
			return;
		}
		String username = e.getName();
		if (username == null) {
			return;
		}
		if (this.checkOnline(username)) {
			e.disallow(Result.KICK_OTHER, cm.getAntiDoubleLoginMessage());
		}
	}

	private boolean checkOnline(String username) {
		try {
			for (World w : Bukkit.getWorlds()) {
				for (Player p : w.getPlayers()) {
					if (p != null && p.getName().equalsIgnoreCase(username)) {
						return true;
					}
				}
			}
		} catch (Exception ignore) {
			// 忽略
		}
		return false;
	}

}
