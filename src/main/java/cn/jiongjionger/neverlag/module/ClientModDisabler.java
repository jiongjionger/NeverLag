package cn.jiongjionger.neverlag.module;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class ClientModDisabler implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (cm.isClientModDisabler()) {
			Player p = e.getPlayer();
			if (p.isOp() || p.hasPermission("neverLag.nosendmagiccode")) return;
			for (String code : cm.getModMagicCode()) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', code));
			}
		}
	}
}
