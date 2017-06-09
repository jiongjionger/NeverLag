package cn.jiongjionger.neverlag.command;

import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.utils.PingUtils;

public class CommandPing {
	// 查看全服在线玩家的实际PING情况

	private void sendSortPingInfo(Player sender) {
		LinkedHashMap<String, Integer> record = PingUtils.getPingAndSort();
		// 还是用GUI显示好了。。
	}
}
