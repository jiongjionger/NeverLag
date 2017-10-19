package cn.jiongjionger.neverlag.module.pandawire.v1_11_R1;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.jiongjionger.neverlag.module.pandawire.PandaWireReflectUtil;
import net.minecraft.server.v1_11_R1.BaseBlockPosition;
import net.minecraft.server.v1_11_R1.Block;
import net.minecraft.server.v1_11_R1.BlockDiodeAbstract;
import net.minecraft.server.v1_11_R1.BlockPiston;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.BlockRedstoneComparator;
import net.minecraft.server.v1_11_R1.BlockRedstoneTorch;
import net.minecraft.server.v1_11_R1.BlockRedstoneWire;
import net.minecraft.server.v1_11_R1.EnumDirection;
import net.minecraft.server.v1_11_R1.IBlockAccess;
import net.minecraft.server.v1_11_R1.IBlockData;
import net.minecraft.server.v1_11_R1.SoundEffectType;
import net.minecraft.server.v1_11_R1.World;

/*
 * @author Panda4994
 */

public class PandaRedstoneWire extends BlockRedstoneWire {

	/*
	 * 作者说用LinkedHashSet替代arraylist没有明显的性能提示，红石设备不多的时候的确如此
	 * 但是实测在红石设备数量很大的时候，有2~5%的性能提升（基于PaperSpigot1.10.2测试），所以还是改用LinkedHashSet来实现
	 */
	// 需要被断路的红石位置
	private Set<BlockPosition> turnOff = Sets.newLinkedHashSet();
	// 需要被激活的红石位置
	private Set<BlockPosition> turnOn = Sets.newLinkedHashSet();
	private final Set<BlockPosition> updatedRedstoneWire = Sets.newLinkedHashSet();
	private static final EnumDirection[] facingsHorizontal = { EnumDirection.WEST, EnumDirection.EAST, EnumDirection.NORTH, EnumDirection.SOUTH };
	private static final EnumDirection[] facingsVertical = { EnumDirection.DOWN, EnumDirection.UP };
	private static final EnumDirection[] facings = (EnumDirection[]) ArrayUtils.addAll(facingsVertical, facingsHorizontal);
	private static final BaseBlockPosition[] surroundingBlocksOffset;

	static {
		Set<BaseBlockPosition> set = Sets.newLinkedHashSet();
		for (EnumDirection facing : facings) {
			set.add(PandaWireReflectUtil.getOfT(facing, BaseBlockPosition.class));
		}
		for (EnumDirection facing1 : facings) {
			BaseBlockPosition v1 = (BaseBlockPosition) PandaWireReflectUtil.getOfT(facing1, BaseBlockPosition.class);
			for (EnumDirection facing2 : facings) {
				BaseBlockPosition v2 = (BaseBlockPosition) PandaWireReflectUtil.getOfT(facing2, BaseBlockPosition.class);

				set.add(new BlockPosition(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ()));
			}
		}
		set.remove(BlockPosition.ZERO);
		surroundingBlocksOffset = (BaseBlockPosition[]) set.toArray(new BaseBlockPosition[set.size()]);
	}

	private boolean g = true;

	public PandaRedstoneWire() {
		c(0.0F);
		a(SoundEffectType.d);
		c("redstoneDust");
		p();
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
			world.a(pos, this);
		}
	}

	private void calculateCurrentChanges(World world, BlockPosition blockposition) {
		if (world.getType(blockposition).getBlock() == this) {
			this.turnOff.add(blockposition);
		} else {
			checkSurroundingWires(world, blockposition);
		}
		while (!this.turnOff.isEmpty()) {
			Iterator<BlockPosition> iter = this.turnOff.iterator();
			final BlockPosition pos = iter.next();
			iter.remove();
			IBlockData state = world.getType(pos);
			int oldPower = state.get(POWER);
			this.g = false;
			int blockPower = world.z(pos);
			this.g = true;
			int wirePower = getSurroundingWirePower(world, pos);

			wirePower--;
			int newPower = Math.max(blockPower, wirePower);
			if (oldPower != newPower) {
				BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ()), oldPower, newPower);
				world.getServer().getPluginManager().callEvent(event);

				newPower = event.getNewCurrent();
			}
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
			int blockPower = world.z(pos);
			this.g = true;
			int wirePower = getSurroundingWirePower(world, pos);

			wirePower--;
			int newPower = Math.max(blockPower, wirePower);
			if (oldPower != newPower) {
				BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ()), oldPower, newPower);
				world.getServer().getPluginManager().callEvent(event);

				newPower = event.getNewCurrent();
			}
			if (newPower > oldPower) {
				state = setWireState(world, pos, state, newPower);
			} else if (newPower >= oldPower) {
			}
			checkSurroundingWires(world, pos);
		}
		this.turnOff.clear();
		this.turnOn.clear();
	}

	private void addWireToList(World worldIn, BlockPosition pos, int otherPower) {
		IBlockData state = worldIn.getType(pos);
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

	private void checkSurroundingWires(World worldIn, BlockPosition pos) {
		IBlockData state = worldIn.getType(pos);
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
			boolean solidBlock = worldIn.getType(offsetPos).l();
			for (EnumDirection facingHorizontal : facingsHorizontal) {
				if (((facingVertical == EnumDirection.UP) && (!solidBlock)) || ((facingVertical == EnumDirection.DOWN) && (solidBlock) && (!worldIn.getType(offsetPos.shift(facingHorizontal)).l()))) {
					addWireToList(worldIn, offsetPos.shift(facingHorizontal), ownPower);
				}
			}
		}
	}

	private int getSurroundingWirePower(World worldIn, BlockPosition pos) {
		int wirePower = 0;
		for (EnumDirection enumfacing : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
			BlockPosition offsetPos = pos.shift(enumfacing);

			wirePower = getPower(worldIn, offsetPos, wirePower);
			if ((worldIn.getType(offsetPos).m()) && (!worldIn.getType(pos.up()).m())) {
				wirePower = getPower(worldIn, offsetPos.up(), wirePower);
			} else if (!worldIn.getType(offsetPos).m()) {
				wirePower = getPower(worldIn, offsetPos.down(), wirePower);
			}
		}
		return wirePower;
	}

	private void addBlocksNeedingUpdate(World worldIn, BlockPosition pos, Set<BlockPosition> set) {
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
					(worldIn.getType(offsetPos).l())) {
				for (EnumDirection facing1 : facings) {
					if (canBlockBePoweredFromSide(worldIn.getType(offsetPos.shift(facing1)), facing1, false)) {
						set.add(offsetPos.shift(facing1));
					}
				}
			}
		}
	}

	private boolean canBlockBePoweredFromSide(IBlockData state, EnumDirection side, boolean isWire) {
		if (((state.getBlock() instanceof BlockPiston)) && (state.get(BlockPiston.FACING) == side.opposite())) {
			return false;
		}
		if (((state.getBlock() instanceof BlockDiodeAbstract)) && (state.get(BlockDiodeAbstract.FACING) != side.opposite())) {
			if ((isWire) && ((state.getBlock() instanceof BlockRedstoneComparator)) && (((EnumDirection) state.get(BlockRedstoneComparator.FACING)).k() != side.k()) && (side.k().c())) {
				return true;
			}
			return false;
		}
		if (((state.getBlock() instanceof BlockRedstoneTorch)) && ((isWire) || (state.get(BlockRedstoneTorch.FACING) != side))) {
			return false;
		}
		return true;
	}

	private List<EnumDirection> getSidesToPower(World worldIn, BlockPosition pos) {
		List<EnumDirection> retval = Lists.newArrayList();
		for (EnumDirection facing : facingsHorizontal) {
			if (c(worldIn, pos, facing)) {
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

	private void addAllSurroundingBlocks(BlockPosition pos, Set<BlockPosition> set) {
		for (BaseBlockPosition vect : surroundingBlocksOffset) {
			set.add(pos.a(vect));
		}
	}

	private IBlockData setWireState(World worldIn, BlockPosition pos, IBlockData state, int power) {
		state = state.set(POWER, power);
		worldIn.setTypeAndData(pos, state, 2);
		this.updatedRedstoneWire.add(pos);
		return state;
	}

	@Override
	public void onPlace(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		if (!world.isClientSide) {
			e(world, blockposition, iblockdata);
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.VERTICAL) {
				world.applyPhysics(blockposition.shift(enumDirection), this, false);
			}
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				b(world, blockposition.shift(enumDirection));
			}
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				BlockPosition blockposition1 = blockposition.shift(enumDirection);
				if (world.getType(blockposition1).m()) {
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
				world.applyPhysics(blockposition.shift(enumdirection), this, false);
			}
			e(world, blockposition, iblockdata);
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				b(world, blockposition.shift(enumDirection));
			}
			for (EnumDirection enumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
				BlockPosition blockposition1 = blockposition.shift(enumDirection);
				if (world.getType(blockposition1).m()) {
					b(world, blockposition1.up());
				} else {
					b(world, blockposition1.down());
				}
			}
		}
	}

	public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
		if (!world.isClientSide) {
			if (canPlace(world, blockposition)) {
				e(world, blockposition, iblockdata);
			} else {
				b(world, blockposition, iblockdata, 0);
				world.setAir(blockposition);
			}
		}
	}

	public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
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

	private boolean c(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
		BlockPosition blockposition1 = blockposition.shift(enumdirection);
		IBlockData iblockdata = iblockaccess.getType(blockposition1);
		boolean flag = iblockdata.l();
		boolean flag1 = iblockaccess.getType(blockposition.up()).l();
		return (!flag1) && (flag) && (c(iblockaccess, blockposition1.up()));
	}
}