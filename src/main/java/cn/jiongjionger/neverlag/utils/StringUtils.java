package cn.jiongjionger.neverlag.utils;

import java.util.Locale;

public class StringUtils {
	public static Locale toLocale(String str) {
		String language;
		String country = "";
		
		int idx = str.indexOf('_');
		if(idx == -1) {
			idx = str.indexOf('-');
		}
		if(idx == -1) {
			language = str;
		} else {
			language = str.substring(0, idx);
			country = str.substring(idx + 1, str.length());
		}
		
		return new Locale(language, country);
	}
}
