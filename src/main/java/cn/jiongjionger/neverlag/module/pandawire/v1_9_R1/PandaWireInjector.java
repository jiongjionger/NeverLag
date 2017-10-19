package cn.jiongjionger.neverlag.module.pandawire.v1_9_R1;

import org.bukkit.Bukkit;

import com.google.common.collect.UnmodifiableIterator;

import cn.jiongjionger.neverlag.module.pandawire.IPandaWireInjector;
import cn.jiongjionger.neverlag.module.pandawire.PandaWireReflectUtil;
import net.minecraft.server.v1_9_R1.Block;
import net.minecraft.server.v1_9_R1.BlockRedstoneWire;
import net.minecraft.server.v1_9_R1.Blocks;
import net.minecraft.server.v1_9_R1.IBlockData;
import net.minecraft.server.v1_9_R1.MinecraftKey;

public class PandaWireInjector implements IPandaWireInjector{

	private static Block get(final String s) {
		return Block.REGISTRY.get(new MinecraftKey(s));
	}

	public static void inject() {
		try {
			register(55, "redstone_wire", new PandaRedstoneWire());
			PandaWireReflectUtil.setStatic("REDSTONE_WIRE", Blocks.class, get("redstone_wire"));
		} catch (Exception e) {
			Bukkit.getLogger().info("[NeverLag]PandaWireInjector inject failed!");
		}
	}

	private static void register(int typeid, String minecraftKey, final Block block) {
		Block.REGISTRY.a(typeid, new MinecraftKey(minecraftKey), block);
		UnmodifiableIterator<IBlockData> iterable = block.t().a().iterator();
		while (iterable.hasNext()) {
			IBlockData iblockdata = iterable.next();
			int k = Block.REGISTRY.a(block) << 4 | block.toLegacyData(iblockdata);
			Block.REGISTRY_ID.a(iblockdata, k);

		}
	}

	public static void uninject() {
		try {
			register(55, "redstone_wire", new BlockRedstoneWire());
			PandaWireReflectUtil.setStatic("REDSTONE_WIRE", Blocks.class, get("redstone_wire"));
		} catch (Exception e) {
			Bukkit.getLogger().info("[NeverLag]PandaWireInjector uninject failed!");
		}
	}
}
