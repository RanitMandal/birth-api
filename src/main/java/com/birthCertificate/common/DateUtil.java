package com.birthCertificate.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static Date str2date(String dateStr, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.parse(dateStr);
	}
	

}
