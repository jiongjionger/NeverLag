package cn.jiongjionger.neverlag.fixer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiCrashChat {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		if (!cm.isAntiCrashChat()) {
			return;
		}
		String chatMessage = e.getMessage();
		if (chatMessage != null && chatMessage.contains("İ")) {
			e.setCancelled(true);
		}
	}
}
