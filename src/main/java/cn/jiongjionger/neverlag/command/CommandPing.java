package cn.jiongjionger.neverlag.command;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.gui.GUISortPing;
import cn.jiongjionger.neverlag.gui.GUISortPingHolder;
import cn.jiongjionger.neverlag.utils.PingUtils;

public class CommandPing extends AbstractSubCommand {

	public CommandPing() {
		super("ping");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			this.showSortPingInfo(p);
		} else {
			this.sendSortPingInfo(sender);
		}
	}

	// 对console直接发送文字
	private void sendSortPingInfo(CommandSender sender) {
		LinkedHashMap<String, Integer> record = PingUtils.getPingAndSort();
		if (record == null) {
			sender.sendMessage(i18n.tr("noPlayer"));
		} else {
			Iterator<Entry<String, Integer>> iterator = record.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Integer> entry = iterator.next();
				StringBuilder sb = new StringBuilder();
				sb.append(entry.getKey()).append(": ").append(PingUtils.colorPing(entry.getValue()));
				sender.sendMessage(sb.toString());
			}
		}
	}

	// 对Player进行GUI展示
	private void showSortPingInfo(Player p) {
		LinkedHashMap<String, Integer> record = PingUtils.getPingAndSort();
		if (record == null) {
			p.sendMessage(i18n.tr("noPlayer"));
		} else {
			GUISortPing guiSlotPing = new GUISortPing(record);
			p.openInventory(guiSlotPing.get());
			GUISortPingHolder.put(p, guiSlotPing);
		}
	}
}
