package com.company.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceIdGenerator {
	public static String attendanceId(String nfcId, Date date, String time) {

		// Format date without hyphens
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = dateFormat.format(date);

		return nfcId + formattedDate + time.substring(0, 1);
	}

}
