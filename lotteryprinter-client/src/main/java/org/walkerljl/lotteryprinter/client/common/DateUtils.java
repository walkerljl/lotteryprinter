package org.walkerljl.lotteryprinter.client.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * DateUtil
 *
 * @author lijunlin
 */
public class DateUtils {
	
	public static String getCurrentDate(String format) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		return simpleDateFormat.format(calendar.getTime());
	}

	public static String getAccurateTime() {
		return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}
}
