package cn.jiongjionger.neverlag.command;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.gui.GUISortPing;
import cn.jiongjionger.neverlag.gui.GUISortPingHolder;
import cn.jiongjionger.neverlag.utils.PingUtils;

public class CommandPing implements ISubCommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final String PERMNODE = "neverlag.command.ping";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("ping")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				this.showSortPingInfo(p);
			} else {
				this.sendSortPingInfo(sender);
			}
		}
		return true;
	}

	// 对Player进行GUI展示
	private void showSortPingInfo(Player p) {
		LinkedHashMap<String, Integer> record = PingUtils.getPingAndSort();
		if (record == null) {
			p.sendMessage(cm.getCommandNoPlayerOnline());
		} else {
			GUISortPing guiSlotPing = new GUISortPing(record);
			p.openInventory(guiSlotPing.get());
			GUISortPingHolder.put(p, guiSlotPing);
		}
	}

	// 对console直接发送文字
	private void sendSortPingInfo(CommandSender sender) {
		LinkedHashMap<String, Integer> record = PingUtils.getPingAndSort();
		if (record == null) {
			sender.sendMessage(cm.getCommandNoPlayerOnline());
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

	public String getPermNode() {
		return this.PERMNODE;
	}

}
