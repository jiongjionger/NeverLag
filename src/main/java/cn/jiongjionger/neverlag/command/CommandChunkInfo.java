package cn.jiongjionger.neverlag.command;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChunkInfo extends AbstractSubCommand {
	private static final List<String> COMPLETION_LIST = Arrays.asList("entity", "tiles", "monsters", "animals", "dropitem", "player", "villager", "squid", "chest", "hopper",
			"furnace", "dropper", "dispenser", "piston", "noteblock", "jukebox", "brewing", "cauldron", "armorstand", "skull");
	
	public CommandChunkInfo() {
		super("chunkinfo", 1);
	}
	
	private List<Chunk> getAllChunk() {
		List<Chunk> chunks = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			chunks.addAll(Arrays.asList(world.getLoadedChunks()));
		}
		return chunks;
	}

	// TODO 查看区块信息 按照指定查询项目进行排序显示 例如按照区块内实体数量/Tiles数量进行排序显示
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		List<Chunk> chunks = this.getAllChunk();
		if (chunks.isEmpty()) {
			sender.sendMessage(i18n.tr("chunkListEmpty"));
			return;
		}
		if (sender instanceof Player) {
			Player p = (Player) sender;
		} else {

		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return COMPLETION_LIST;
	}
}
