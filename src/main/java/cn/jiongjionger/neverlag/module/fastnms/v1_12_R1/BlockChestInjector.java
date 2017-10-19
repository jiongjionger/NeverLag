package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import net.minecraft.server.v1_12_R1.BlockChest;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.SoundEffectType;
import net.minecraft.server.v1_12_R1.TileEntity;
import net.minecraft.server.v1_12_R1.World;

public class BlockChestInjector extends BlockChest {

	protected BlockChestInjector(final Type type) {
		super(type);
		// 材质强度
		c(2.5f);
		// 声音
		a(SoundEffectType.a);
	}

	@Override
	public TileEntity a(final World world, final int n) {
		return new FastTileEntityChest();
	}

	@SuppressWarnings("unused")
	private boolean j(final World world, final BlockPosition blockPosition) {
		// 不再检测箱子顶部是否坐着豹猫
		return false;
	}
}
