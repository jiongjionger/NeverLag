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

	private ConfigManager cm = ConfigManager.getInstance();
	private final BlockFace[] BLOCKFACE = { BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };

	@EventHandler
	public void onDamageSkull(PlayerInteractEvent e) {
		if (cm.isAntiDamageSkull) {
			if (Action.RIGHT_CLICK_BLOCK.equals(e.getAction())) {
				if (e.getItem() != null) {
					Material type = e.getItem().getType();
					if (Material.LAVA_BUCKET.equals(type) || Material.WATER_BUCKET.equals(type)) {
						fixSkull(e.getClickedBlock().getRelative(BlockFace.UP));
					} else if (Material.ANVIL.equals(type)) {
						for (BlockFace face : BLOCKFACE) {
							fixSkull(e.getClickedBlock().getRelative(face));
						}
					}
				}
			}
		}
	}

	private void fixSkull(Block block) {
		if (block != null && Material.SKULL.equals(block.getType())) {
			Skull skull = (Skull) block.getState();
			if (!skull.getSkullType().equals(SkullType.SKELETON)) {
				final FixSkullTask task = new FixSkullTask(block.getLocation(), skull.getSkullType(), skull.getOwner());
				Bukkit.getServer().getScheduler().runTaskLater(NeverLag.getInstance(), task, 1L);
			}
		}
	}

	public class FixSkullTask implements Runnable {

		private Location loc;
		private SkullType type;
		private String owner;

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
			if (!b.getType().equals(Material.SKULL)) {
				return;
			}
			Skull skull = (Skull) b.getState();
			if (type.equals(SkullType.PLAYER)) {
				skull.setSkullType(type);
				skull.setOwner(owner);
			} else {
				skull.setSkullType(type);
			}
			skull.update();
		}
	}
}
