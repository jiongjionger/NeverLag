package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import java.util.Random;

import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.CreativeModeTab;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.Items;
import net.minecraft.server.v1_12_R1.Material;
import net.minecraft.server.v1_12_R1.World;

public class FastBlockSnowBlock extends Block {
	
	protected FastBlockSnowBlock() {
		super(Material.SNOW_BLOCK);
		// a(true);
		a(CreativeModeTab.b);
	}

	public Item getDropType(final IBlockData iblockdata, final Random random, final int i) {
		return Items.SNOWBALL;
	}

	public int a(final Random random) {
		return 4;
	}

	public void b(final World world, final BlockPosition blockposition, final IBlockData iblockdata, final Random random) {
		/*
		 * 雪方块根本不依赖于光照，也不会融化，这个计算是多余的
		 * 
		 * if (world.getBrightness(EnumSkyBlock.BLOCK, blockposition) > 11) { if
		 * (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(
		 * blockposition.getX(), blockposition.getY(), blockposition.getZ()),
		 * Blocks.AIR).isCancelled()) { return; } b(world, blockposition,
		 * world.getType(blockposition), 0); world.setAir(blockposition); }
		 */

	}
}