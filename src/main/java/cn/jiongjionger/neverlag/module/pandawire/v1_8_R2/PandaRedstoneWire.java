package cn.jiongjionger.neverlag.module.pandawire.v1_8_R2;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.jiongjionger.neverlag.module.pandawire.PandaWireReflectUtil;
import net.minecraft.server.v1_8_R2.BaseBlockPosition;
import net.minecraft.server.v1_8_R2.Block;
import net.minecraft.server.v1_8_R2.BlockDiodeAbstract;
import net.minecraft.server.v1_8_R2.BlockDirectional;
import net.minecraft.server.v1_8_R2.BlockPiston;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.BlockRedstoneComparator;
import net.minecraft.server.v1_8_R2.BlockRedstoneTorch;
import net.minecraft.server.v1_8_R2.BlockRedstoneWire;
import net.minecraft.server.v1_8_R2.BlockTorch;
import net.minecraft.server.v1_8_R2.EnumDirection;
import net.minecraft.server.v1_8_R2.IBlockAccess;
import net.minecraft.server.v1_8_R2.IBlockData;
import net.minecraft.server.v1_8_R2.World;

/*
 * @author Panda4994
 */

public class PandaRedstoneWire extends BlockRedstoneWire {

	// facing方向的枚举
	private static final EnumDirection[] facingsHorizontal = { EnumDirection.WEST, EnumDirection.EAST, EnumDirection.NORTH, EnumDirection.SOUTH };
	// facing垂直的枚举
	private static final EnumDirection[] facingsVertical = { EnumDirection.DOWN, EnumDirection.UP };
	// 上述两者之和
	private static final EnumDirection[] facings = ArrayUtils.addAll(facingsVertical, facingsHorizontal);
	// facing的X、Y、Z的偏移量位置数组
	private static final BaseBlockPosition[] surroundingBlocksOffset;
	// 初始化surroundingBlocksOffset
	static {
		Set<BaseBlockPosition> set = Sets.newLinkedHashSet();
		for (EnumDirection facing : facings) {
			set.add(PandaWireReflectUtil.getOfT(facing, BaseBlockPosition.class));
		}
		for (EnumDirection facing1 : facings) {
			BaseBlockPosition v1 = PandaWireReflectUtil.getOfT(facing1, BaseBlockPosition.class);
			for (EnumDirection facing2 : facings) {
				BaseBlockPosition v2 = PandaWireReflectUtil.getOfT(facing2, BaseBlockPosition.class);
				set.add(new BlockPosition(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ()));
			}
		}
		set.remove(BlockPosition.ZERO);
		surroundingBlocksOffset = set.toArray(new BaseBlockPosition[set.size()]);
	}
	/*
	 * 作者说用LinkedHashSet替代arraylist没有明显的性能提示，红石设备不多的时候的确如此
	 * 但是实测在红石设备数量很大的时候，有2~5%的性能提升（基于PaperSpigot1.10.2测试），所以还是改用LinkedHashSet来实现
	 */
	// 需要被断路的红石位置
	private Set<BlockPosition> turnOff = Sets.newLinkedHashSet();
	// 需要被激活的红石位置
	private Set<BlockPosition> turnOn = Sets.newLinkedHashSet();

	// 已经更新的红石线路
	private final Set<BlockPosition> updatedRedstoneWire = Sets.newLinkedHashSet();

	private boolean g = true;

	public PandaRedstoneWire() {
		c(0.0F);
		a(Block.e);
		c("redstoneDust");
		K();
	}

	@Override
	public int a(final IBlockAccess iblockaccess, final BlockPosition blockposition, final IBlockData iblockdata, final EnumDirection enumdirection) {
		if (!this.g) {
			return 0;
		}
		int i = iblockdata.get(BlockRedstoneWire.POWER);
		if (i == 0) {
			return 0;
		}
		if (enumdirection == EnumDirection.UP) {
			return i;
		}
		if (getSidesToPower((World) iblockaccess, blockposition).contains(enumdirection)) {
			return i;
		}
		return 0;
	}

	private void addAllSurroundingBlocks(final BlockPosition pos, final Set<BlockPosition> set) {
		for (BaseBlockPosition vect : surroundingBlocksOffset) {
			set.add(pos.a(vect));
		}
	}

	private void addBlocksNeedingUpdate(final World worldIn, final BlockPosition pos, final Set<BlockPosition> set) {
		List<EnumDirection> connectedSides = getSidesToPower(worldIn, pos);
		for (EnumDirection facing : facings) {
			BlockPosition offsetPos = pos.shift(facing);
			if (((connectedSides.contains(facing.opposite())) || (facing == EnumDirection.DOWN) || ((facing.k().c()) && (a(worldIn.getType(offsetPos), facing)))) &&
					(canBlockBePoweredFromSide(worldIn.getType(offsetPos), facing, true))) {
				set.add(offsetPos);
			}
		}
		for (EnumDirection facing : facings) {
			BlockPosition offsetPos = pos.shift(facing);
			if (((connectedSides.contains(facing.opposite())) || (facing == EnumDirection.DOWN)) &&
					(worldIn.getType(offsetPos).getBlock().isOccluding())) {
				for (EnumDirection facing1 : facings) {
					if (canBlockBePoweredFromSide(worldIn.getType(offsetPos.shift(facing1)), facing1, false)) {
						set.add(offsetPos.shift(facing1));
					}
				}
			}
		}
	}

	// 添加红石线路到需要被断路/激活的List
	private void addWireToList(final World worldIn, final BlockPosition pos, final int otherPower) {
		final IBlockData state = worldIn.getType(pos);
		if (state.getBlock() == this) {
			int power = state.get(POWER);
			if ((power < otherPower - 1) && (!this.turnOn.contains(pos))) {
				this.turnOn.add(pos);
			}
			if ((power > otherPower) && (!this.turnOff.contains(pos))) {
				this.turnOff.add(pos);
			}
		}
	}

	private void calculateCurrentChanges(final World world, final BlockPosition blockposition) {
		if (world.getType(blockposition).getBlock() == this) {
			this.turnOff.add(blockposition);
		} else {
			checkSurroundingWires(world, blockposition);
		}
		// 遍历递减充能等级
		while (!this.turnOff.isEmpty()) {
			Iterator<BlockPosition> iter = this.turnOff.iterator();
			final BlockPosition pos = iter.next();
			iter.remove();
			IBlockData state = world.getType(pos);
			int oldPower = state.get(POWER);
			this.g = false;
			int blockPower = world.A(pos);
			this.g = true;
			int wirePower = getSurroundingWirePower(world, pos);

			wirePower--;
			int newPower = Math.max(blockPower, wirePower);
			if (newPower < oldPower) {
				if ((blockPower > 0) && (!this.turnOn.contains(pos))) {
					this.turnOn.add(pos);
				}
				state = setWireState(world, pos, state, 0);
			} else if (newPower > oldPower) {
				state = setWireState(world, pos, state, newPower);
			}
			checkSurroundingWires(world, pos);
		}
		while (!this.turnOn.isEmpty()) {
			Iterator<BlockPosition> iter = this.turnOn.iterator();
			final BlockPosition pos = iter.next();
			iter.remove();
			IBlockData state = world.getType(pos);
			int oldPower = state.get(POWER);
			this.g = false;
			int blockPower = world.A(pos);
			this.g = true;
			int wirePower = getSurroundingWirePower(world, pos);

			wirePower--;
			int newPower = Math.max(blockPower, wirePower);
			if (oldPower != newPower) {
				// 充能等级发现变化，触发BlockRedstoneEvent事件
				BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), oldPower, newPower);
				world.getServer().getPluginManager().callEvent(event);
				newPower = event.getNewCurrent();
			}
			if (newPower > oldPower) {
				state = setWireState(world, pos, state, newPower);
			}
			checkSurroundingWires(world, pos);
		}
		this.turnOff.clear();
		this.turnOn.clear();
	}

	private boolean canBlockBePoweredFromSide(final IBlockData state, final EnumDirection side, final boolean isWire) {
		if (((state.getBlock() instanceof BlockPiston)) && (state.get(BlockPiston.FACING) == side.opposite())) {
			return false;
		}
		if (((state.getBlock() instanceof BlockDiodeAbstract)) && (state.get(BlockDirectional.FACING) != side.opposite())) {
			if ((isWire) && ((state.getBlock() instanceof BlockRedstoneComparator)) && (state.get(BlockDirectional.FACING).k() != side.k()) && (side.k().c())) {
				return true;
			}
			return false;
		}
		if (((state.getBlock() instanceof BlockRedstoneTorch)) && ((isWire) || (state.get(BlockTorch.FACING) != side))) {
			return false;
		}
		return true;
	}

	// 遍历facing枚举检测附近的红石线路
	private void checkSurroundingWires(final World worldIn, final BlockPosition pos) {
		final IBlockData state = worldIn.getType(pos);
		int ownPower = 0;
		if (state.getBlock() == this) {
			ownPower = state.get(POWER);
		}
		for (EnumDirection facing : facingsHorizontal) {
			BlockPosition offsetPos = pos.shift(facing);
			if (facing.k().c()) {
				addWireToList(worldIn, offsetPos, ownPower);
			}
		}
		for (EnumDirection facingVertical : facingsVertical) {
			BlockPosition offsetPos = pos.shift(facingVertical);
			boolean solidBlock = worldIn.getType(offsetPos).getBlock().u();
			for (EnumDirection facingHorizontal : facingsHorizontal) {
				if (((facingVertical == EnumDirection.UP) && (!solidBlock))
						|| ((facingVertical == EnumDirection.DOWN) && (solidBlock) && (!worldIn.getType(offsetPos.shift(facingHorizontal)).getBlock().isOccluding()))) {
					addWireToList(worldIn, offsetPos.shift(facingHorizontal), ownPower);
				}
			}
		}
	}

	private boolean d(final IBlockAccess iblockaccess, final BlockPosition blockposition, final EnumDirection enumdirection) {
		BlockPosition blockposition1 = blockposition.shift(enumdirection);
		IBlockData iblockdata = iblockaccess.getType(blockposition1);
		Block block = iblockdata.getBlock();
		boolean blockisOccluding = block.isOccluding();
		boolean upBlockisOccluding = iblockaccess.getType(blockposition.up()).getBlock().isOccluding();
		return (!upBlockisOccluding) && (blockisOccluding) && (e(iblockaccess, blockposition1.up()));
	}

	@Override
	public void doPhysics(final World world, final BlockPosition blockposition, final IBlockData iblockdata, final Block block) {
		if (!world.isClientSide) {
			if (canPlace(world, blockposition)) {
				e(world, blockposition, iblockdata);
			} else {
				b(world, blockposition, iblockdata, 0);
				world.setAir(blockposition);
			}
		}
	}

	private void e(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		calculateCurrentChanges(world, blockposition);
		Set<BlockPosition> blocksNeedingUpdate = Sets.newLinkedHashSet();
		Iterator<BlockPosition> iterator = this.updatedRedstoneWire.iterator();
		while (iterator.hasNext()) {
			addBlocksNeedingUpdate(world, iterator.next(), blocksNeedingUpdate);
		}
		Iterator<BlockPosition> blockPositionIterator = Lists.newLinkedList(this.updatedRedstoneWire).descendingIterator();
		while (blockPositionIterator.hasNext()) {
			addAllSurroundingBlocks(blockPositionIterator.next(), blocksNeedingUpdate);
		}
		blocksNeedingUpdate.removeAll(this.updatedRedstoneWire);
		this.updatedRedstoneWire.clear();
		for (BlockPosition pos : blocksNeedingUpdate) {
			world.d(pos, this);
		}
	}

	private List<EnumDirection> getSidesToPower(final World worldIn, final BlockPosition pos) {
		List<EnumDirection> retval = Lists.newArrayList();
		for (EnumDirection facing : facingsHorizontal) {
			if (d(worldIn, pos, facing)) {
				retval.add(facing);
			}
		}
		if (retval.isEmpty()) {
			return Lists.newArrayList(facingsHorizontal);
		}
		boolean northsouth = (retval.contains(EnumDirection.NORTH)) || (retval.contains(EnumDirection.SOUTH));
		boolean eastwest = (retval.contains(EnumDirection.EAST)) || (retval.contains(EnumDirection.WEST));
		if (northsouth) {
			retval.remove(EnumDirection.EAST);
			retval.remove(EnumDirection.WEST);
		}
		if (eastwest) {
			retval.remove(EnumDirection.NORTH);
			retval.remove(EnumDirection.SOUTH);
		}
		return retval;
	}

	// 计算周围的红石线路来统计当前位置的红石充能等级
	private int getSurroundingWirePower(final World worldIn, final BlockPosition pos) {
		int wirePower = 0;
		for (EnumDirection enumfacing : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
			BlockPosition offsetPos = pos.shift(enumfacing);
			wirePower = getPower(worldIn, offsetPos, wirePower);
			if ((worldIn.getType(offsetPos).getBlock().isOccluding()) && (!worldIn.getType(pos.up()).getBlock().isOccluding())) {
				wirePower = getPower(worldIn, offsetPos.up(), wirePower);
			} else if (!worldIn.getType(offsetPos).getBlock().isOccluding()) {
				wirePower = getPower(worldIn, offsetPos.down(), wirePower);
			}
		}
		return wirePower;
	}

	@Override
	public void onPlace(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		if (!world.isClientSide) {
			e(world, blockposition, iblockdata);
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.VERTICAL) {
				world.applyPhysics(blockposition.shift(enumDirection), this);
			}
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				b(world, blockposition.shift(enumDirection));
			}
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				BlockPosition blockposition1 = blockposition.shift(enumDirection);
				if (world.getType(blockposition1).getBlock().isOccluding()) {
					b(world, blockposition1.up());
				} else {
					b(world, blockposition1.down());
				}
			}
		}
	}

	@Override
	public void remove(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		super.remove(world, blockposition, iblockdata);
		if (!world.isClientSide) {
			EnumDirection[] aenumdirection = EnumDirection.values();
			int i = aenumdirection.length;
			for (int j = 0; j < i; j++) {
				EnumDirection enumdirection = aenumdirection[j];
				world.applyPhysics(blockposition.shift(enumdirection), this);
			}
			e(world, blockposition, iblockdata);
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				b(world, blockposition.shift(enumDirection));
			}
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				BlockPosition blockposition1 = blockposition.shift(enumDirection);
				if (world.getType(blockposition1).getBlock().isOccluding()) {
					b(world, blockposition1.up());
				} else {
					b(world, blockposition1.down());
				}
			}
		}
	}

	private IBlockData setWireState(final World worldIn, final BlockPosition pos, IBlockData state, final int power) {
		state = state.set(POWER, power);
		worldIn.setTypeAndData(pos, state, 2);
		this.updatedRedstoneWire.add(pos);
		return state;
	}
}