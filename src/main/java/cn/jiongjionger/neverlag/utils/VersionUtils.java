package cn.jiongjionger.neverlag.utils;

import org.bukkit.Bukkit;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionUtils {

	/*
	 * 延迟加载处理. 在服务器未启动的情况下试图读取当前版本会抛错.
	 */
	private static final class CurrentVersionHolder {
		private static final Version currentVersion = new Version(getCurrentMinecraftVersion());
	}

	public static class Version implements Comparable<Version>, Serializable {

		private static final long serialVersionUID = 1L;
		private static final Pattern DEVELOPMENT_VERSION_PATTERN = Pattern.compile("-|(\\d{2}w\\d{2})([a-z])");

		private final int major;
		private final int minor;
		private final int build;

		public Version(int major, int minor, int build) {
			this.major = major;
			this.minor = minor;
			this.build = build;
		}

		public Version(String versionString) {
			Matcher matcher = DEVELOPMENT_VERSION_PATTERN.matcher(versionString);
			if (matcher.find()) { // 检查版本字符串里是否包含连字符(-)或类似 15w36a 形式的快照版本号
				throw new IllegalStateException("此插件不支持开发版/快照版服务端");
			}

			String[] sections = versionString.split("\\.");
			int[] versions = new int[3];

			if (sections.length < 1) {
				throw new IllegalStateException(versionString);
			}

			try {
				for (int i = 0; i < Math.min(sections.length, versions.length); i++) {
					versions[i] = Integer.parseInt(sections[i]);
				}
			} catch (NumberFormatException ex) {
				throw new IllegalStateException(versionString, ex);
			}

			this.major = versions[0];
			this.minor = versions[1];
			this.build = versions[2];
		}

		@Override
		public int compareTo(Version other) {
			int r;
			if ((r = Integer.compare(this.major, other.major)) != 0)
				return r;
			if ((r = Integer.compare(this.minor, other.minor)) != 0)
				return r;
			if ((r = Integer.compare(this.build, other.build)) != 0)
				return r;
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			final Version other = (Version) obj;
			return this.major == other.major && this.minor == other.minor && this.build == other.build;
		}

		public int getBuild() {
			return build;
		}

		public int getMajor() {
			return major;
		}

		public int getMinor() {
			return minor;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 67 * hash + this.major;
			hash = 67 * hash + this.minor;
			hash = 67 * hash + this.build;
			return hash;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(6);
			builder.append(major).append('.').append(minor);
			if (build != 0)
				builder.append('.').append(build);
			return builder.toString();
		}
	}

	public static final Version V1_6 = new Version("1.6");
	public static final Version V1_7 = new Version("1.7");
	public static final Version V1_8 = new Version("1.8");
	public static final Version V1_9 = new Version("1.9");
	public static final Version V1_10 = new Version("1.10");
	public static final Version V1_11 = new Version("1.11");

	public static final Version V1_12 = new Version("1.12");

	public static final Version V1_13 = new Version("1.13");

	private static final Pattern MC_VERSION_PATTERN = Pattern.compile(".*\\(.*MC.\\s*([\\w\\-.]+)\\s*\\)");

	/** @return e.g. 1.11.2 */
	public static String extractVersion(String bukkitVersion) {
		Matcher matcher = MC_VERSION_PATTERN.matcher(bukkitVersion);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	/** @see #extractVersion(java.lang.String) */
	public static String getCurrentMinecraftVersion() {
		return extractVersion(Bukkit.getVersion());
	}

	public static Version getCurrentVersion() {
		return CurrentVersionHolder.currentVersion;
	}

	public static boolean isAtLeast(Version version) {
		return getCurrentVersion().compareTo(version) >= 0;
	}

	public static boolean isLowerThan(Version version) {
		return !isAtLeast(version);
	}

	/** 是否为Mod服. */
	public static boolean isModServer() {
		String bukkitName = Bukkit.getName().toLowerCase();
		return bukkitName.contains("mcpc") || bukkitName.contains("cauldron");
	}

	private VersionUtils() {
	}
}