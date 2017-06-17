package cn.jiongjionger.neverlag.utils;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PermUtils {

	public static int getMaxPerm(Player p, String node) {
		int maxLimit = 0;
		for (PermissionAttachmentInfo perm : p.getEffectivePermissions()) {
			String permission = perm.getPermission();
			if (!permission.startsWith("node")) {
				continue;
			}
			String[] split = permission.split("\\.");
			try {
				int number = Integer.parseInt(split[split.length - 1]);
				if (number > maxLimit) {
					maxLimit = number;
				}
			} catch (Exception e) {
				continue;
			}
		}
		return maxLimit;
	}
}
