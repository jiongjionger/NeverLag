package cn.jiongjionger.neverlag.module.fastnms.v1_12_R1;

import net.minecraft.server.v1_12_R1.BlockChest;

public class BlockTrappedChestInjector extends BlockChestInjector{
	
	public BlockTrappedChestInjector() {
		super(BlockChest.Type.TRAP);
		c("chestTrap");
	}
}
