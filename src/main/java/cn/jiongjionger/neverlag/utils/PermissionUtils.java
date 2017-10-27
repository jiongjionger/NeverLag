package cn.jiongjionger.neverlag.utils;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public final class PermissionUtils {

	public static int getMaxPermission(Player p, String node) {
		int maxLimit = 0;
		for (PermissionAttachmentInfo perm : p.getEffectivePermissions()) {
			String permission = perm.getPermission();
			if (!permission.toLowerCase().startsWith(node.toLowerCase())) {
				continue;
			}
			String[] split = permission.split("\\.");
			try {
				int number = Integer.parseInt(split[split.length - 1]);
				if (number > maxLimit) {
					maxLimit = number;
				}
			} catch (NumberFormatException ignore) {
				// just ignore it
			}
		}
		return maxLimit;
	}

	private PermissionUtils() {}
}
