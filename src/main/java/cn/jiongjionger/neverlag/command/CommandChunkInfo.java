package cn.jiongjionger.neverlag.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class CommandChunkInfo implements ISubCommandExecutor {

	private final ConfigManager cm = ConfigManager.getInstance();
	private final String PERMNODE = "neverlag.command.chunkinfo";

	private List<Chunk> getAllChunk() {
		List<Chunk> chunks = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			chunks.addAll(Arrays.asList(world.getLoadedChunks()));
		}
		return chunks;
	}

	@Override
	public String getPermNode() {
		return this.PERMNODE;
	}

	// 查看区块信息 按照指定查询项目进行排序显示 例如按照区块内实体数量/Tiles数量进行排序显示
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("neverlag") && args.length >= 1 && args[0].equalsIgnoreCase("chunkinfo")) {
			if (args.length == 1) {
				// TO DO 发送提示，没有输入排序参数
			}
			List<Chunk> chunks = this.getAllChunk();
			if (chunks.isEmpty()) {
				// TO DO 发送提示，没有加载的区块
				return true;
			}
			if (sender instanceof Player) {
				Player p = (Player) sender;
			} else {

			}
		}
		return true;
	}
}
