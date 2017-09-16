package cn.jiongjionger.neverlag.hardware;

public final class WMI4JavaUtil {

	public static String join(String delimiter, Iterable<?> parts) {
		StringBuilder joinedString = new StringBuilder();
		for (final Object part : parts) {
			joinedString.append(part);
			joinedString.append(delimiter);
		}
		joinedString.delete(joinedString.length() - delimiter.length(), joinedString.length());
		return joinedString.toString();
	}

	private WMI4JavaUtil() {}
}
