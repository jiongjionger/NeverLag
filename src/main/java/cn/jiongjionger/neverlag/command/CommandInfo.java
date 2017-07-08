package cn.jiongjionger.neverlag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.ServerInfo;
import cn.jiongjionger.neverlag.utils.WorldInfo;

public class CommandInfo implements ISubCommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final String PERMNODE = "neverlag.command.info";

	@Override
	public String getPermNode() {
		return this.PERMNODE;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("info")) {
			ServerInfo serverInfo = new ServerInfo();
			String baseinfoMessage = cm.getCommandInfoBaseMessage()
					.replace("%TPS%", String.valueOf(serverInfo.getRealtimeTPS()))
					.replace("%AVGTPS%", String.valueOf(serverInfo.getAvgTPS()))
					.replace("%REDSTONE%", String.valueOf(serverInfo.getRealtimeRedstone()))
					.replace("%AVGREDSTONE%", String.valueOf(serverInfo.getAvgRedstone()))
					.replace("%UPTIME%", serverInfo.getServerUpTime())
					.replace("%MAXMEMORY%", String.valueOf(serverInfo.getRuntimeMaxMemory()))
					.replace("%TOTALMEMORY%", String.valueOf(serverInfo.getRuntimeTotalMemory()))
					.replace("%USEDMEMORY%", String.valueOf(serverInfo.getRuntimeUsedMemory()))
					.replace("%FREEMEMORY%", String.valueOf(serverInfo.getRuntimeAvailableMemory()));
			for (String message : baseinfoMessage.split("\n")) {
				sender.sendMessage(message);
			}
			for (WorldInfo worldInfo : serverInfo.getWorldInfo()) {
				String wordInfoMessage = cm.getCommandWorldInfoMessage()
						.replace("%WORLDNAME%", worldInfo.getWorldName())
						.replace("%CHUNK%", String.valueOf(worldInfo.getTotalChunk()))
						.replace("%ENTITY%", String.valueOf(worldInfo.getTotalEntity()))
						.replace("%TILES%", String.valueOf(worldInfo.getTotalTiles()))
						.replace("%ONLINE%", String.valueOf(worldInfo.getTotalOnline()))
						.replace("%MONSTERS%", String.valueOf(worldInfo.getTotalMonsters()))
						.replace("%ANIMALS%", String.valueOf(worldInfo.getTotalAnimals()))
						.replace("%CHEST%", String.valueOf(worldInfo.getTotalChest()))
						.replace("%HOPPER%", String.valueOf(worldInfo.getTotalHopper()))
						.replace("%FURNACE%", String.valueOf(worldInfo.getTotalFurnace()))
						.replace("%DISPENSER%", String.valueOf(worldInfo.getTotalDispenser()))
						.replace("%DROPPER%", String.valueOf(worldInfo.getTotalDropper()))
						.replace("%BRWEINGSTAND%", String.valueOf(worldInfo.getTotalBrewingStand()))
						.replace("%DROPITEM%", String.valueOf(worldInfo.getTotalDropItem()));
				for (String message : wordInfoMessage.split("\n")) {
					sender.sendMessage(message);
				}
			}
		}
		return true;
	}
}
