package cn.jiongjionger.neverlag.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

public class VersionUtils {

	public static final VersionUtils v1_6 = new VersionUtils(0);
	public static final VersionUtils v1_7 = new VersionUtils(1);
	public static final VersionUtils v1_8 = new VersionUtils(2);
	public static final VersionUtils v1_9 = new VersionUtils(3);
	public static final VersionUtils v1_10 = new VersionUtils(4);
	public static final VersionUtils v1_11 = new VersionUtils(5);
	public static final VersionUtils v1_12 = new VersionUtils(6);
	public static final VersionUtils v1_13 = new VersionUtils(7);
	
	
	private static VersionUtils serverVersion;
	private int versionValue;

	private VersionUtils(int versionValue) {
		this.versionValue = versionValue;
	}

	public static VersionUtils getVersion() {
		return serverVersion;
	}

	public static void set(VersionUtils version) {
		VersionUtils.serverVersion = version;
	}

	public static boolean isAtLeast(VersionUtils version) {
		return VersionUtils.getVersion().versionValue >= version.versionValue;
	}

	public static boolean isLowThan(VersionUtils version) {
		return VersionUtils.getVersion().versionValue < version.versionValue;
	}

	public static void init() {
		String version = getBukkitVersion();
		if (version.startsWith("v1_6_R")) {
			VersionUtils.set(VersionUtils.v1_6);
		} else if (version.startsWith("v1_7_R")) {
			VersionUtils.set(VersionUtils.v1_7);
		} else if (version.startsWith("v1_8_R")) {
			VersionUtils.set(VersionUtils.v1_8);
		} else if (version.startsWith("v1_9_R")) {
			VersionUtils.set(VersionUtils.v1_9);
		} else if (version.startsWith("v1_10_R")) {
			VersionUtils.set(VersionUtils.v1_10);
		} else if (version.startsWith("v1_11_R")) {
			VersionUtils.set(VersionUtils.v1_11);
		} else if (version.startsWith("v1_12_R")) {
			VersionUtils.set(VersionUtils.v1_12);
		} else if (version.startsWith("v1_13_R")) {
			VersionUtils.set(VersionUtils.v1_13);
		}
		if (VersionUtils.getVersion() == null) {
			throw new NullPointerException("minecraft server version match error");
		}
	}

	// 返回格式为V1_12R1
	public static String getBukkitVersion() {
		Matcher matcher = Pattern.compile("v\\d+_\\d+_R\\d+").matcher(Bukkit.getServer().getClass().getPackage().getName());
		if (matcher.find()) {
			return matcher.group();
		} else {
			return null;
		}
	}

	// 返回格式为1.11.2
	public static String getMinecraftVersion() {
		Matcher matcher = Pattern.compile("(\\(MC: )([\\d\\.]+)(\\))").matcher(Bukkit.getVersion());
		if (matcher.find()) {
			return matcher.group(2);
		} else {
			return null;
		}
	}

	// 是否为Mod服
	public static boolean isModServer() {
		String bukkitName = Bukkit.getName().toLowerCase();
		return bukkitName.contains("mcpc") || bukkitName.contains("cauldron");
	}
}
