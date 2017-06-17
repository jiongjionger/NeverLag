package cn.jiongjionger.neverlag.controler;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class CommandAndChatCooldown implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();
	// 保存命令间隔时间列表
	private HashMap<String, Long> commandCoolDown = new HashMap<String, Long>();
	// 保存聊天间隔时间列表
	private ConcurrentHashMap<String, Long> chatCoolDown = new ConcurrentHashMap<String, Long>();

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		// 忽略OP
		if (p.isOp()) {
			return;
		}
		// 忽略免检权限
		if (p.hasPermission("ChatCommandRate.Pass")) {
			return;
		}
		// 命令白名单
		String command[] = e.getMessage().toLowerCase().split(" ");
		if (command.length >= 1) {
			if (cm.getCommandCooldownWhiteList().contains(command[0])) {
				return;
			}
		}
		// 判断间隔时间
		String username = e.getPlayer().getName();
		long now = System.currentTimeMillis();
		if (commandCoolDown.containsKey(username)) {
			long lastUseCommandTime = commandCoolDown.get(username);
			if (now - lastUseCommandTime <= cm.getCommandCooldownTime()) {
				e.setCancelled(true);
				p.sendMessage(cm.getCommandCooldownMessage());
				return;
			}
		}
		commandCoolDown.put(username, now);
	}

	// 限制聊天频率
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		// 忽略OP
		Player p = e.getPlayer();
		if (p.isOp()) {
			return;
		}
		// 忽略免检权限
		if (p.hasPermission("ChatCommandRate.Pass")) {
			return;
		}
		// 判断间隔时间
		String username = p.getName();
		long now = System.currentTimeMillis();
		Long lastChatTime;
		if ((lastChatTime = chatCoolDown.get(username)) != null) {
			if (now - lastChatTime <= cm.getChatCooldownTime()) {
				e.setCancelled(true);
				p.sendMessage(cm.getChatCooldownMessage());
				return;
			}
		}
		chatCoolDown.put(username, now);

	}

	@EventHandler
	// 用户退出时清理出HashMap
	public void onQuit(PlayerQuitEvent e) {
		String username = e.getPlayer().getName();
		commandCoolDown.remove(username);
		chatCoolDown.remove(username);
	}

}
