package com.company.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OptOutIdGenerator {
	public static String optId(String studentId, Date date, String time) {

		// Format date without hyphens
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = dateFormat.format(date);

		return studentId + formattedDate + time.substring(0, 1);
	}

}
