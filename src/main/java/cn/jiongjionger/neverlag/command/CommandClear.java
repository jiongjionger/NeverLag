package cn.jiongjionger.neverlag.command;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;

import cn.jiongjionger.neverlag.utils.EntityUtils;

public class CommandClear extends AbstractSubCommand {
	private static final List<String> COMPLETION_LIST;
	
	static {
		String[] others = {"monsters", "monster", "animals", "animal", "dropitems", "dropitem", "items", "item"};
		
		// 需要排除 Player 类型, 所以得 -1s
		List<String> list = new ArrayList<>(others.length + EntityType.values().length - 1);
		list.addAll(Arrays.asList(others));
		for(EntityType type : EntityType.values()) {
			if(type != EntityType.PLAYER) {
				@SuppressWarnings("deprecation")
				String name = type.getName().toLowerCase();
				list.add(name);
			}
		}
		COMPLETION_LIST = Collections.unmodifiableList(list);
	}

	public CommandClear() {
		super("clear", 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		String type = args[0].toLowerCase();
		if(!COMPLETION_LIST.contains(type)) {
			// TODO 提示没有此类型
			throw new UnsupportedOperationException();
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
		sender.sendMessage(cm.getCommandClearMessage().replace("%TYPE%", args[1]).replace("%COUNT%", String.valueOf(count)));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return COMPLETION_LIST;
	}

	@Override
	public String getUsage() {
		return cm.getCommandClearNoTypeArg();
	}
}
