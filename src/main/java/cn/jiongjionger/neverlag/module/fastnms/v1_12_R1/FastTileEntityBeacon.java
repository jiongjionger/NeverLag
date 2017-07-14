package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import java.util.List;

import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Blocks;
import net.minecraft.server.v1_12_R1.CriterionTriggers;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MobEffect;
import net.minecraft.server.v1_12_R1.MobEffectList;
import net.minecraft.server.v1_12_R1.TileEntityBeacon;

public class FastTileEntityBeacon extends TileEntityBeacon {

	@Override
	public void n() {
		if (this.world != null) {
			// F();
			// E();
			this.applyEffectToHumansInRange();
			this.checkBase();
		}
	}

	/*
	 * 检测信标的是否满足被激活的条件
	 * 优化点1：检测方块材质的时候先检测方块所在区块是否被加载，避免信标反复加载->卸载->加载区块
	 * 优化点2：在循环检测的时候，一旦不符合直接return，NMS使用boolean类型的flag记录是否满足条件却在最后才判断flag
	 * 优化点3：信标光柱的颜色和信标上方是否存在不透明放开遮挡，这是client-side的，服务端检测毫无必要
	 * 优化点4：信标放在不可能有基座的位置直接不计算
	 */
	private void checkBase() {
		final int y = this.position.getY();
		// 直接忽略放在最低处和非法的情况
		if (y <= 0 || y > 255) {
			return;
		}
		final int x = this.position.getX();
		int z = this.position.getZ();
		int level = this.levels;
		this.levels = 0;
		// 通过坐标获得Y轴最高的方块位置
		final BlockPosition highestBlockPosition = this.world.getHighestBlockYAt(this.position);
		if (highestBlockPosition.getY() > y) {
			return;
		}
		int maxLevel = 4;
		int shouldCheckY;
		Block shouldCheckBlock;
		BlockPosition shouldCheckBlockPosition;
		// 检测结构
		while (maxLevel > 0) {
			// 计算需要检测的Y轴
			shouldCheckY = y - maxLevel;
			maxLevel--;
			for (int baseX = x - level; baseX <= x + level; baseX++) {
				for (int baseZ = z - level; baseZ <= z + level; baseZ++) {
					// 获取方块，如果区块没加载或者不符合直接返回
					shouldCheckBlockPosition = new BlockPosition(baseX, shouldCheckY, baseZ);
					// 预防检测信标结构导致区块频繁被加载->卸载->加载
					if (!this.world.isLoaded(shouldCheckBlockPosition)) {
						return;
					}
					shouldCheckBlock = this.world.getType(shouldCheckBlockPosition).getBlock();
					if (shouldCheckBlock != Blocks.DIAMOND_BLOCK && shouldCheckBlock != Blocks.GOLD_BLOCK && shouldCheckBlock != Blocks.IRON_BLOCK && shouldCheckBlock != Blocks.EMERALD_BLOCK) {
						return;
					}
				}
			}
			// 计算信标等级
			this.levels++;
		}
		// 等级改变触发construct_beacon成就
		if ((!this.world.isClientSide) && level < this.levels) {
			for (EntityPlayer entityPlayer : this.world.a(EntityPlayer.class, new AxisAlignedBB(x, y, z, x, y - 4, z).grow(10.0D, 5.0D, 10.0D))) {
				CriterionTriggers.k.a(entityPlayer, this);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void applyEffectToHumansInRange() {
		if (this.levels > 0 && (!this.world.isClientSide) && this.primaryEffect != null) {
			final byte b0 = (byte) (this.levels >= 4 && this.primaryEffect == this.secondaryEffect ? 1 : 0);
			final int i = (9 + this.levels * 2) * 20;
			final List<EntityHuman> list = getHumansInRange();
			applyEffect(list, this.primaryEffect, i, b0);
			if (hasSecondaryEffect()) {
				applyEffect(list, this.secondaryEffect, i, 0);
			}
		}
	}

	private void applyEffect(final List<EntityHuman> list, final MobEffectList effects, final int i, final int b0) {
		for (final EntityHuman entityhuman : list) {
			entityhuman.addEffect(new MobEffect(effects, i, b0, true, true));
		}
	}

	private boolean hasSecondaryEffect() {
		if ((this.levels >= 4) && (this.primaryEffect != this.secondaryEffect) && (this.secondaryEffect != null)) {
			return true;
		}
		return false;
	}
}
