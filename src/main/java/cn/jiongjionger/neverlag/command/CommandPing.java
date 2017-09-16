package cn.jiongjionger.neverlag.command;

import cn.jiongjionger.neverlag.gui.GUISortPing;
import cn.jiongjionger.neverlag.gui.GUISortPingHolder;
import cn.jiongjionger.neverlag.utils.PingUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
			for (Entry<String, Integer> entry : record.entrySet()) {
				sender.sendMessage(entry.getKey() + ": " + PingUtils.colorPing(entry.getValue()));
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
