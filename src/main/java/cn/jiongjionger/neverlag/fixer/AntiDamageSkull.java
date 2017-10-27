package cn.jiongjionger.neverlag.fixer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;

public class AntiDamageSkull implements Listener {

	public class FixSkullTask implements Runnable {

		private final Location loc;
		private final SkullType type;
		private final String owner;

		public FixSkullTask(Location location, SkullType skulltype, String skullowner) {
			this.loc = location;
			this.type = skulltype;
			this.owner = skullowner;
		}

		@Override
		public void run() {
			if (loc == null) {
				return;
			}
			Block b = loc.getBlock();
			if (b.getType() != Material.SKULL) {
				return;
			}
			Skull skull = (Skull) b.getState();
			if (type == SkullType.PLAYER) {
				skull.setSkullType(type);
				skull.setOwner(owner);
			} else {
				skull.setSkullType(type);
			}
			skull.update();
		}
	}

	private final ConfigManager cm = ConfigManager.getInstance();

	private final BlockFace[] BLOCKFACE = { BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };

	private void fixSkull(Block block) {
		if (block != null && Material.SKULL == block.getType()) {
			Skull skull = (Skull) block.getState();
			if (skull.getSkullType() != SkullType.SKELETON) {
				final FixSkullTask task = new FixSkullTask(block.getLocation(), skull.getSkullType(), skull.getOwner());
				Bukkit.getServer().getScheduler().runTaskLater(NeverLag.getInstance(), task, 1L);
			}
		}
	}

	@EventHandler
	public void onDamageSkull(PlayerInteractEvent e) {
		if (cm.isAntiDamageSkull) {
			if (Action.RIGHT_CLICK_BLOCK == e.getAction()) {
				if (e.getItem() != null) {
					Material type = e.getItem().getType();
					if (Material.LAVA_BUCKET == type || Material.WATER_BUCKET == type) {
						fixSkull(e.getClickedBlock().getRelative(BlockFace.UP));
					} else if (Material.ANVIL == type) {
						for (BlockFace face : BLOCKFACE) {
							fixSkull(e.getClickedBlock().getRelative(face));
						}
					}
				}
			}
		}
	}
}
