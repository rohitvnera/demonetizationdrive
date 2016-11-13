package com.ampdev.platform.module.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	public static long getCurrentDateInMilliSec()
	{
		Date d = new Date();
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0);
		return d.getTime();
	}
	
	public static long getAnotherDateInMilliSec(Date d, int diff, int diffType)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(diffType, diff);
		return calendar.getTimeInMillis();
	}
	
	public static long getAnotherDateInMilliSec(Long d, int diff, int diffType)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(d));
		calendar.add(diffType, diff);
		return calendar.getTimeInMillis();
	}
}
