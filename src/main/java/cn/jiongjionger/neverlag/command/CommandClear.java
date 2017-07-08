package cn.jiongjionger.neverlag.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.EntityUtils;

public class CommandClear implements ISubCommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final String PERMNODE = "neverlag.command.clear";

	@Override
	public String getPermNode() {
		return this.PERMNODE;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("clear")) {
			if (args.length == 1) {
				sender.sendMessage(cm.getCommandClearNoTypeArg());
				return true;
			}
			int count = 0;
			for (World w : Bukkit.getWorlds()) {
				for (Entity entity : w.getEntities()) {
					if (EntityUtils.checkCustomNpc(entity) || entity instanceof Player) {
						continue;
					}
					switch (args[1].toLowerCase()) {
					case "monsters":
						if (entity instanceof Monster) {
							entity.remove();
							count++;
						}
						break;
					case "animals":
						if (entity instanceof Animals) {
							entity.remove();
							count++;
						}
						break;
					case "dropitem":
						if (entity instanceof Item) {
							entity.remove();
							count++;
						}
						break;
					default:
						break;
					}
					if (entity.getType().getName().equalsIgnoreCase(args[1])) {
						entity.remove();
						count++;
					}
				}
			}
			sender.sendMessage(cm.getCommandClearMessage().replace("%TYPE%", args[1]).replace("%COUNT%", String.valueOf(count)));
		}
		return true;
	}
}
