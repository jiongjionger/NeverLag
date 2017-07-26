package cn.jiongjionger.neverlag.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import java.util.Random;

public class ProtocolLibUtils {

	private static ProtocolManager plm;
	private static Random random = new Random();

	public static ProtocolManager get() {
		if (plm == null) {
			plm = ProtocolLibrary.getProtocolManager();
		}
		return plm;
	}

	public static void sendKeepAlive(List<Player> player) {
		for (Player p : player) {
			try {
				PacketContainer packet = get().createPacket(PacketType.Play.Client.KEEP_ALIVE);
				packet.getIntegers().write(0, random.nextInt());
				get().sendServerPacket(p, packet);
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			}
		}
	}
}
