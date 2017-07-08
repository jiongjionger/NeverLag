package cn.jiongjionger.neverlag.module.pandawire.v1_8_R2;

import org.bukkit.Bukkit;

import cn.jiongjionger.neverlag.module.pandawire.PandaWireReflectUtil;
import net.minecraft.server.v1_8_R2.Block;
import net.minecraft.server.v1_8_R2.BlockRedstoneWire;
import net.minecraft.server.v1_8_R2.Blocks;
import net.minecraft.server.v1_8_R2.IBlockData;
import net.minecraft.server.v1_8_R2.MinecraftKey;

public class PandaWireInjector {

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
		for (IBlockData iblockdata : block.P().a()) {
			Block.d.a(iblockdata, Block.REGISTRY.b(block) << 4 | block.toLegacyData(iblockdata));
		}
	}

	public static void unject() {
		try {
			register(55, "redstone_wire", new BlockRedstoneWire());
			PandaWireReflectUtil.setStatic("REDSTONE_WIRE", Blocks.class, get("redstone_wire"));
		} catch (Exception e) {
			Bukkit.getLogger().info("[NeverLag]PandaWireInjector uninject failed!");
		}
	}
}
