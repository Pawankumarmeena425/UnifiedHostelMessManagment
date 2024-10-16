package com.company.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackIdGenerator {

	public String feedbackId(String studentId, Date date, String time) {

		// Format date without hyphens
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String formattedDate = dateFormat.format(date);

		return studentId + formattedDate + time.substring(0, 1);
	}
}
