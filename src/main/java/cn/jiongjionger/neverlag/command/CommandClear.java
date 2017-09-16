package cn.jiongjionger.neverlag.command;


import cn.jiongjionger.neverlag.utils.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandClear extends AbstractSubCommand {
	private static final List<String> COMPLETION_LIST;

	static {
		List<String> list = new ArrayList<>(Arrays.asList("monsters", "monster", "animals", "animal", "dropitems", "dropitem", "items", "item"));
		for (EntityType type : EntityType.values()) {
			if (type != EntityType.PLAYER) {
				@SuppressWarnings("deprecation")
				String name = type.getName();
				if (name != null) {
					list.add(name.toLowerCase());
				}
			}
		}
		COMPLETION_LIST = Collections.unmodifiableList(new ArrayList<>(list));
	}

	public CommandClear() {
		super("clear", 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		String type = args[0].toLowerCase();
		if (!COMPLETION_LIST.contains(type)) {
			sender.sendMessage(i18n.tr("illegalType", COMPLETION_LIST));
			return;
		}
		int count = 0;
		for (World w : Bukkit.getWorlds()) {
			for (Entity entity : w.getEntities()) {
				if (entity instanceof Player || EntityUtils.checkCustomNpc(entity)) {
					continue;
				}
				switch (args[0].toLowerCase()) {
				case "monsters":
				case "monster":
					if (entity instanceof Monster) {
						entity.remove();
						count++;
					}
					break;
				case "animals":
				case "animal":
					if (entity instanceof Animals) {
						entity.remove();
						count++;
					}
					break;
				case "dropitems":
				case "dropitem":
				case "items":
				case "item":
					if (entity instanceof Item) {
						entity.remove();
						count++;
					}
					break;
				default:
					if (entity.getType().getName().equalsIgnoreCase(args[0])) {
						entity.remove();
						count++;
					}
					break;
				}
			}
		}
		sender.sendMessage(i18n.tr("success", args[0], count));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return COMPLETION_LIST;
	}
}
