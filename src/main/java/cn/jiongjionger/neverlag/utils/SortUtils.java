package cn.jiongjionger.neverlag.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class SortUtils {
	
	public static LinkedHashMap<String, Integer> sortMapByValues(HashMap<String, Integer> map) {
		if (map.isEmpty()) {
			return null;
		}
		Set<Entry<String, Integer>> entries = map.entrySet();
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(entries);
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		LinkedHashMap<String, Integer> sortMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortMap.put(entry.getKey(), entry.getValue());
		}
		return sortMap;
	}
}
