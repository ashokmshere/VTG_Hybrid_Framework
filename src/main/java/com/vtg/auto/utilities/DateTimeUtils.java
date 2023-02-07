
package com.vtg.auto.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeUtils {

	public static String getCurrentDateWithFormat(String format) {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = formatter.format(today);
		return formattedDate;
	}

	public static String getFormattedStartTime(long time, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedStartTime = formatter.format(calendar.getTime());
		return formattedStartTime;
	}

	public static Date getStringAsDate(String date, String applicationDateFormat) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(applicationDateFormat);
			return formatter.parse(date);
		} catch (ParseException e) {
			throw new Exception("Unable to parse date - > " + date);
		}
	}

	public String AddDaysToCurrentDate(int days, String applicationDateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(applicationDateFormat);
		Calendar calen = Calendar.getInstance();
		calen.setTime(new Date());
		calen.add(Calendar.DATE, days);
		return formatter.format(calen.getTime());
	}

}