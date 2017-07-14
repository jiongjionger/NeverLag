package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import net.minecraft.server.v1_12_R1.Blocks;
import net.minecraft.server.v1_12_R1.SoundCategory;
import net.minecraft.server.v1_12_R1.SoundEffects;
import net.minecraft.server.v1_12_R1.TileEntityEnderChest;

public class FastTileEntityEnderChest extends TileEntityEnderChest {

	@Override
	public void e() {
		// 不再每tick计算、发送声音
	}

	@Override
	public void a() {
		this.g += 1;
		// 只在打开箱子的时候计算并且发送声音
		if (this.g > 0 && this.a == 0.0F) {
			this.a = 0.7F;
			final double d1 = this.getPosition().getX() + 0.5D;
			final double d0 = this.getPosition().getZ() + 0.5D;
			this.world.a(null, d1, this.getPosition().getY() + 0.5D, d0, SoundEffects.aT, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
		}
		// End
		this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
	}

	@Override
	public void f() {
		this.g -= 1;
		// 只在关闭箱子的时候计算并且发送声音
		if (this.g == 0 && this.a > 0.0F || this.g > 0 && this.a < 1.0F) {
			final double d0 = this.getPosition().getX() + 0.5D;
			final double d2 = this.getPosition().getZ() + 0.5D;
			this.world.a(null, d0, this.getPosition().getY() + 0.5D, d2, SoundEffects.aS, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			this.a = 0.0F;
		}
		// End
		this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
	}
}
