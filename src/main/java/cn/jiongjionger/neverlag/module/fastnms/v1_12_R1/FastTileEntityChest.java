package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import org.bukkit.craftbukkit.v1_12_R1.event.CraftEventFactory;

import net.minecraft.server.v1_12_R1.BlockChest;
import net.minecraft.server.v1_12_R1.Blocks;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.SoundCategory;
import net.minecraft.server.v1_12_R1.SoundEffects;
import net.minecraft.server.v1_12_R1.TileEntityChest;

public class FastTileEntityChest extends TileEntityChest {

	@Override
	public void e() {
		// 不再每tick计算、发送声音
	}

	// 打开容器
	@Override
	public void startOpen(final EntityHuman entityhuman) {
		if (!entityhuman.isSpectator()) {
			if (this.l < 0) {
				this.l = 0;
			}
			final int oldPower = Math.max(0, Math.min(15, this.l));
			this.l += 1;
			this.o();
			// 只有打开箱子的时候才计算、发送声音
			if (this.l > 0 && this.j == 0.0F && this.f == null && this.h == null) {
				this.j = 0.7F;
				double d0 = (double) this.position.getZ() + 0.5D;
				double d1 = (double) this.position.getX() + 0.5D;
				if (this.i != null) {
					d0 += 0.5D;
				}
				if (this.g != null) {
					d1 += 0.5D;
				}
				this.world.a((EntityHuman) null, d1, (double) this.position.getY() + 0.5D, d0, SoundEffects.ac, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
			// End
			if (this.world == null) {
				return;
			}
			this.world.playBlockAction(this.position, getBlock(), 1, this.l);
			if (getBlock() == Blocks.TRAPPED_CHEST) {
				int newPower = Math.max(0, Math.min(15, this.l));
				if (oldPower != newPower) {
					CraftEventFactory.callRedstoneChange(this.world, this.position.getX(), this.position.getY(), this.position.getZ(), oldPower, newPower);
				}
			}
			this.world.applyPhysics(this.position, getBlock(), false);
			if (p() == BlockChest.Type.TRAP) {
				this.world.applyPhysics(this.position.down(), getBlock(), false);
			}
		}
	}

	// 关闭容器
	@Override
	public void closeContainer(final EntityHuman entityhuman) {
		if ((!entityhuman.isSpectator()) && ((getBlock() instanceof BlockChest))) {
			final int oldPower = Math.max(0, Math.min(15, this.l));
			this.l -= 1;
			// // 只有关闭箱子的时候才计算、发送声音
			if (this.l == 0 && this.j > 0.0F || this.l > 0 && this.j < 1.0F) {
				final float f = 0.1F;
				if (this.l > 0) {
					this.j += f;
				} else {
					this.j -= f;
				}
				double d0 = (double) this.getPosition().getX() + 0.5D;
				double d2 = (double) this.getPosition().getZ() + 0.5D;
				int yLoc = this.position.getY();
				if (this.i != null) {
					d2 += 0.5D;
				}
				if (this.g != null) {
					d0 += 0.5D;
				}
				this.world.a((EntityHuman) null, d0, (double) yLoc + 0.5D, d2, SoundEffects.aa, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
				this.j = 0.0F;
			}
			// End
			this.world.playBlockAction(this.position, getBlock(), 1, this.l);
			this.world.applyPhysics(this.position, getBlock(), false);
			if (p() == BlockChest.Type.TRAP) {
				int newPower = Math.max(0, Math.min(15, this.l));
				if (oldPower != newPower) {
					CraftEventFactory.callRedstoneChange(this.world, this.position.getX(), this.position.getY(), this.position.getZ(), oldPower, newPower);
				}
				this.world.applyPhysics(this.position.down(), getBlock(), false);
			}
		}
	}
}
