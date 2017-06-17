package cn.jiongjionger.neverlag.utils;

import org.bukkit.entity.Entity;

/**
 *
 * @author andylizi
 */
public class EntityUtils {

	public static boolean checkCustomNpc(Entity entity) {
		return entity == null || entity.hasMetadata("NPC") || entity.hasMetadata("MyPet");
	}

	// 实用程序类封闭构造器
	private EntityUtils() throws AssertionError {
		throw new AssertionError();
	}
}
