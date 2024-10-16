package com.company.servlets.University;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.LoginPassword;
import com.company.entities.University;
import com.company.helper.OTPAuthentaction;

public class SignupUniversity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignupUniversity() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to generate a random OTP
	private int generateOTP() {
		Random random = new Random();
		return 100000 + random.nextInt(900000); // Generate a 6-digit OTP
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//
//		response.setHeader("Access-Control-Allow-Origin", "http://192.168.228.241:5173");// The path where you will be running
//																					// the REACT SPA
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Expose-Headers", "Location");
//		response.setHeader("Access-Control-Allow-Credentials", "true"); // Important to allow the client to send Cookie
		// information with its requests

//		response.addHeader("Access-Control-Allow-Origin", "*");

		// Retrieve form data
		String universityName = request.getParameter("university_name");
		String universityEmail = request.getParameter("university_email");
		String mob = request.getParameter("university_mobile");
		String location = request.getParameter("location");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
//		Long mobile = Long.parseLong(mob);
		Long mobile = null;

		PrintWriter out = response.getWriter();
		System.out.println("Hello Request has to come!!");
		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			out.println("Password is Mismathch!!");
			return;
		} else {

			// Generate OTP
			int otp = generateOTP();

			// Send OTP to the user (not implemented)
			// Store OTP in session
			HttpSession session = request.getSession(true);
			session.setAttribute("otpStored", otp + "");
			session.setAttribute("universityEmail", universityEmail);

			String sessionId = session.getId();
			System.out.println(sessionId);
			OTPAuthentaction otpAut = new OTPAuthentaction();
			otpAut.otpSender(universityEmail, otp + "");
			
			out.println(otp);


//			out.println("OTP Send Successfully!!");

		}

	}

}
