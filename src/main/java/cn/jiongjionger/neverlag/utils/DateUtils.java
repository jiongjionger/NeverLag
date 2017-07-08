package cn.jiongjionger.neverlag.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.jiongjionger.neverlag.config.ConfigManager;

public class DateUtils {

	private static ConfigManager cm = ConfigManager.getInstance();

	private static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future) {
		int diff = 0;
		long savedDate = fromDate.getTimeInMillis();
		while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
			savedDate = fromDate.getTimeInMillis();
			fromDate.add(type, future ? 1 : -1);
			diff++;
		}
		diff--;
		fromDate.setTimeInMillis(savedDate);
		return diff;
	}

	public static String formatDateDiff(Calendar fromDate, Calendar toDate) {
		boolean future = false;
		if (toDate.equals(fromDate)) {
			return cm.translate("now");
		}
		if (toDate.after(fromDate)) {
			future = true;
		}
		StringBuilder sb = new StringBuilder();
		int[] types = new int[] { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND };
		String[] names = new String[] { cm.translate("year"), cm.translate("years"), cm.translate("month"), cm.translate("months"), cm.translate("day"),
				cm.translate("days"), cm.translate("hour"), cm.translate("hours"), cm.translate("minute"), cm.translate("minutes"), cm.translate("second"), cm.translate("seconds") };
		int accuracy = 0;
		for (int i = 0; i < types.length; i++) {
			if (accuracy > 2) {
				break;
			}
			int diff = dateDiff(types[i], fromDate, toDate, future);
			if (diff > 0) {
				accuracy++;
				sb.append(" ").append(diff).append(" ").append(names[i * 2 + (diff > 1 ? 1 : 0)]);
			}
		}
		if (sb.length() == 0) {
			return "now";
		}
		return sb.toString().trim();
	}

	public static String formatDateDiff(long date) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(date);
		Calendar now = new GregorianCalendar();
		return DateUtils.formatDateDiff(now, c);
	}
}
