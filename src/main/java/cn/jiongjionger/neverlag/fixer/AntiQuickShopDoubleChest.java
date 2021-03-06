package cn.jiongjionger.neverlag.fixer;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class AntiQuickShopDoubleChest implements Listener {
	private static final BlockFace[] BLOCKFACE = { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };

	/*
	 * 判断某个方块附近是否有对应的某种方块（东南西北方向）
	 *
	 * @param b 需要判断的方块
	 *
	 * @param type 需要判断的方块类型
	 *
	 * @return 附近的方块，没有返回null
	 */
	private static Block getBlockNearby(Block b, Material type) {
		Block relativeBlock;
		for (BlockFace face : BLOCKFACE) {
			relativeBlock = b.getRelative(face);
			if (relativeBlock != null && type == relativeBlock.getType()) {
				return relativeBlock;
			}
		}
		return null;
	}

	private final ConfigManager cm = ConfigManager.getInstance();
	private final I18n i18n = NeverLag.i18n("bugFix");

	/*
	 * 判断某个方块附近是否存在商店箱子方块
	 * 
	 * @param b 需要判断的方块
	 * 
	 * @return 是否存在箱子方块
	 */
	private boolean isShopBlockNearby(Block b) {
		if (b == null) {
			return false;
		}
		Block nearChest = null;
		if (b.getType() == Material.CHEST) {
			nearChest = getBlockNearby(b, Material.CHEST);
		} else if (b.getType() == Material.TRAPPED_CHEST) {
			nearChest = getBlockNearby(b, Material.TRAPPED_CHEST);
		}
		if (nearChest == null) {
			return false;
		}
		for (BlockFace face : BLOCKFACE) {
			Block maybeSign = nearChest.getRelative(face);
			if (maybeSign != null && Material.WALL_SIGN == maybeSign.getType()) {
				Sign sign = (Sign) maybeSign.getState();
				if (sign.getLines().length > 0 && sign.getLines()[0].contains(cm.quickshopSignFlag)) {
					return true;
				}
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlaceChest(BlockPlaceEvent e) {
		if (!cm.isAntiQuickDoubleChest) {
			return;
		}
		Block block = e.getBlock();
		if (block == null) {
			return;
		}
		Material blockType = block.getType();
		if (Material.CHEST == blockType || Material.TRAPPED_CHEST == blockType) {
			if (isShopBlockNearby(block)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(i18n.tr("antiQuickShopDoubleChest"));
			}
		}
	}
}
