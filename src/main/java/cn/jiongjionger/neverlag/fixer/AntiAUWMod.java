package cn.jiongjionger.neverlag.fixer;

import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.utils.ProtocolLibUtils;
import cn.jiongjionger.neverlag.utils.Reflection;
import cn.jiongjionger.neverlag.utils.Reflection.MethodInvoker;

public class AntiAUWMod {

	private static MethodInvoker method_asNMSCopy = null;
	private static MethodInvoker method_hasTag = null;

	public static void init() {
		try {
			method_asNMSCopy = Reflection.getMethod(Reflection.getCraftBukkitClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);
			method_hasTag = Reflection.getMethod(Reflection.getMinecraftClass("ItemStack"), "hasTag");
			ProtocolLibUtils.get().addPacketListener(
					new PacketAdapter(NeverLag.getInstance(), PacketType.Play.Client.SET_CREATIVE_SLOT) {
						@Override
						public void onPacketReceiving(PacketEvent e) {
							onSetCreativeSlotPacket(e);
						}
					});
		} catch (Exception ignore) {
			// ignore
		}
	}

	private static void onSetCreativeSlotPacket(PacketEvent e) {
		if (method_asNMSCopy == null || method_hasTag == null) {
			return;
		}
		if (!e.getPlayer().isOp()) {
			try {
				StructureModifier<ItemStack> structureModifier = e.getPacket().getItemModifier();
				if (structureModifier != null) {
					ItemStack item = structureModifier.read(0);
					if (item != null) {
						Object craftItem = method_asNMSCopy.invoke(null, item);
						if (craftItem != null) {
							Object hasTag = method_hasTag.invoke(craftItem);
							if (hasTag != null && (Boolean) hasTag) {
								e.setCancelled(true);
							}
						}
					}
				}
			} catch (Exception ex) {
				// ignore
			}
		}
	}
}
