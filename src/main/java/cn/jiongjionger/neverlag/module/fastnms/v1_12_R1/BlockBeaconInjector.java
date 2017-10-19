package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import net.minecraft.server.v1_12_R1.BlockBeacon;
import net.minecraft.server.v1_12_R1.TileEntity;
import net.minecraft.server.v1_12_R1.World;

public class BlockBeaconInjector extends BlockBeacon {

	public BlockBeaconInjector() {
		c("beacon");
		a(1.0F);
	}

	@Override
	public TileEntity a(final World world, final int n) {
		return new FastTileEntityBeacon();
	}
}
