package cn.jiongjionger.neverlag.controler;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CommandAndChatCooldown implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final I18n i18n = NeverLag.i18n("cooldown.commandCooldown");
	// 保存命令间隔时间列表
	private final HashMap<String, Long> commandCoolDown = new HashMap<>();
	// 保存聊天间隔时间列表
	private final ConcurrentHashMap<String, Long> chatCoolDown = new ConcurrentHashMap<>();

	// 限制聊天频率
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		// 忽略OP
		Player p = e.getPlayer();
		if (p.isOp()) {
			return;
		}
		// 忽略免检权限
		if (p.hasPermission("neverlag.bypass.cooldown.chat")) {
			return;
		}
		// 判断间隔时间
		String username = p.getName();
		long now = System.currentTimeMillis();
		Long lastChatTime;
		if ((lastChatTime = chatCoolDown.get(username)) != null) {
			long remain = cm.commandCooldownTime - (now - lastChatTime);
			if (remain > 0) {
				e.setCancelled(true);
				p.sendMessage(i18n.tr("chat", Math.ceil(remain / 1000)));
				return;
			}
		}
		chatCoolDown.put(username, now);

	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		// 忽略OP
		if (p.isOp()) {
			return;
		}
		// 忽略免检权限
		if (p.hasPermission("neverlag.bypass.cooldown.command")) {
			return;
		}
		// 命令白名单
		// TODO 修复大小写的问题
		String[] command = e.getMessage().toLowerCase().split(" ");
		if (command.length >= 1) {
			if (cm.commandCooldownWhiteList.contains(command[0])) {
				return;
			}
		}
		// 判断间隔时间
		String username = e.getPlayer().getName();
		long now = System.currentTimeMillis();
		Long lastCommandTime;
		if((lastCommandTime = commandCoolDown.get(username)) != null) {
			long remain = cm.commandCooldownTime - (now - lastCommandTime);
			if (remain > 0) {
				e.setCancelled(true);
				p.sendMessage(i18n.tr("command", Math.ceil(remain / 1000)));
				return;
			}
		}
		commandCoolDown.put(username, now);
	}

	@EventHandler
	// 用户退出时清理出HashMap
	public void onQuit(PlayerQuitEvent e) {
		String username = e.getPlayer().getName();
		commandCoolDown.remove(username);
		chatCoolDown.remove(username);
	}

}
