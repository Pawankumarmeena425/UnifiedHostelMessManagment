package com.company.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MealIdGenerator {
	public static String mealId(String messId, Date date, String time) {

		// Format date without hyphens
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = dateFormat.format(date);

		return messId + formattedDate + time.substring(0, 1);
	}

}
