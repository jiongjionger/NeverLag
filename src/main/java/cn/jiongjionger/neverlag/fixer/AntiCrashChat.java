package cn.jiongjionger.neverlag.fixer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.VersionUtils;

public class AntiCrashChat implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		if (cm.isAntiCrashChat() && VersionUtils.isLowThan(VersionUtils.v1_8)) {
			String chatMessage = e.getMessage();
			if (chatMessage != null && chatMessage.contains("İ")) {
				e.setCancelled(true);
			}
		}
	}
}
