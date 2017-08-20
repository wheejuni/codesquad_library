package com.codesquadlibrary.spring.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStringHandler {

	public static String getTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

}
