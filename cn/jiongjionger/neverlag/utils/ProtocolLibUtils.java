package cn.jiongjionger.neverlag.utils;

import java.util.List;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class ProtocolLibUtils {

	private static ProtocolManager plm;

	public static ProtocolManager get() {
		if (plm == null) {
			plm = ProtocolLibrary.getProtocolManager();
		}
		return plm;
	}

	public static void sendKeepAlive(List<Player> player) {
		try {
			for (Player p : player) {
				get().sendServerPacket(p, get().createPacket(PacketType.Play.Client.KEEP_ALIVE));
			}
		} catch (Exception ignore) {
		}
	}
}
