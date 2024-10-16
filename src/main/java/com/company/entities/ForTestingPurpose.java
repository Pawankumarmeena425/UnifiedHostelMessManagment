package com.company.entities;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.company.helper.OTPAuthentaction;

public class ForTestingPurpose {

	private static final String Name = "D:\\FULL STACK JAVA DEVELOPMENT\\Hostel Mess Management/FinancialSample.xlsx";

	private static String getTime() {
		LocalTime currentTime = LocalTime.now();

		// Define the cut-off time
		LocalTime cutOffTime = LocalTime.of(16, 0); // 4:00 PM

		// Check if the current time is before or after the cut-off time
		if (currentTime.isBefore(cutOffTime)) {
			System.out.println("Morning");
			return "Morning";

		} else {
			System.out.println("Evening");
			return "Evening";
		}
	}

	private static int generateOTP() {
		Random random = new Random();
		return 100000 + random.nextInt(900000); // Generate a 6-digit OTP
	}

	public static void main(String[] args) {
		int otp = generateOTP();

		OTPAuthentaction otpAut = new OTPAuthentaction();
		String email = "maheshme2002@gmail.com";

		for (int i = 0; i < 1000; i++) {
			otpAut.otpSender(email, otp + "");
			System.out.println("Otp Send!! ");
		}

	}

}
