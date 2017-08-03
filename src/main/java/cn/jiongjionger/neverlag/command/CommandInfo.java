package cn.jiongjionger.neverlag.command;

import org.bukkit.command.CommandSender;
import cn.jiongjionger.neverlag.utils.ServerInfo;
import cn.jiongjionger.neverlag.utils.WorldInfo;

public class CommandInfo extends AbstractSubCommand {

	public CommandInfo() {
		super("info");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		ServerInfo serverInfo = new ServerInfo();
		String baseinfoMessage = cm.commandInfoBaseMessage
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
			String wordInfoMessage = cm.commandWorldInfoMessage
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

	@Override
	public String getUsage() {
		return null;
	}
}
