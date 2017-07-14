package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import net.minecraft.server.v1_12_R1.BlockEnderChest;
import net.minecraft.server.v1_12_R1.SoundEffectType;
import net.minecraft.server.v1_12_R1.TileEntity;
import net.minecraft.server.v1_12_R1.World;

public class BlockEnderChestInjector extends BlockEnderChest {

	public BlockEnderChestInjector() {
		// 材质强度
		c(22.5f);
		// 耐久度
		b(1000.0f);
		// 声音
		a(SoundEffectType.d);
		c("enderChest");
		a(0.5f);
	}

	@Override
	public TileEntity a(final World world, final int n) {
		return new FastTileEntityEnderChest();
	}

}
