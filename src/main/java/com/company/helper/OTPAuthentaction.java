
package com.company.helper;

public class OTPAuthentaction {

	public static boolean otpSender(String to, String otp) {
		GEmailSender gmail = new GEmailSender();

//		to = "pawankuamrmeena425@gmail.com";
		String from = "pawankuamrmeena9@gmail.com";
		String subject = "Otp Send From The MBM University!!";
		String text = "This is an Auto Generated Email For OTP Authentaction. \n Please Enter the Below OTP for verfication!!\n\n"
				+ otp + "\n\nGood Day!!";

		boolean b = gmail.sendEmail(to, from, subject, text);

		if (b) {
			return true;
		} else {
			return false;
		}

	}

}
