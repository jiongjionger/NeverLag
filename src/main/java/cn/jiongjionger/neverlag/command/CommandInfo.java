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
		String baseInfo = i18n.tr("base")
				.replace("%TPS%", String.valueOf(serverInfo.getRealtimeTPS()))
				.replace("%AVG_TPS%", String.valueOf(serverInfo.getAvgTPS()))
				.replace("%REDSTONE%", String.valueOf(serverInfo.getRealtimeRedstone()))
				.replace("%AVG_REDSTONE%", String.valueOf(serverInfo.getAvgRedstone()))
				.replace("%UPTIME%", serverInfo.getServerUpTime())
				.replace("%MAX_MEMORY%", String.valueOf(serverInfo.getRuntimeMaxMemory()))
				.replace("%TOTAL_MEMORY%", String.valueOf(serverInfo.getRuntimeTotalMemory()))
				.replace("%USED_MEMORY%", String.valueOf(serverInfo.getRuntimeUsedMemory()))
				.replace("%FREE_MEMORY%", String.valueOf(serverInfo.getRuntimeAvailableMemory()));
		sender.sendMessage(baseInfo.split("\n"));
		for (WorldInfo info : serverInfo.getWorldInfo()) {
			String worldInfo = i18n.tr("world")
					.replace("%WORLDNAME%", info.getWorldName())
					.replace("%CHUNK%", String.valueOf(info.getTotalChunk()))
					.replace("%ONLINE%", String.valueOf(info.getTotalOnline()))
					.replace("%ENTITY%", String.valueOf(info.getTotalEntity()))
					.replace("%MONSTER%", String.valueOf(info.getTotalMonsters()))
					.replace("%ANIMAL%", String.valueOf(info.getTotalAnimals()))
					.replace("%TILE%", String.valueOf(info.getTotalTiles()))
					.replace("%CHEST%", String.valueOf(info.getTotalChest()))
					.replace("%HOPPER%", String.valueOf(info.getTotalHopper()))
					.replace("%FURNACE%", String.valueOf(info.getTotalFurnace()))
					.replace("%DISPENSER%", String.valueOf(info.getTotalDispenser()))
					.replace("%DROPPER%", String.valueOf(info.getTotalDropper()))
					.replace("%BRWEINGSTAND%", String.valueOf(info.getTotalBrewingStand()))
					.replace("%DROPITEM%", String.valueOf(info.getTotalDropItem()));
			sender.sendMessage(worldInfo.split("\n"));
		}
	}

	@Override
	public String getUsage() {
		return null;
	}
}
