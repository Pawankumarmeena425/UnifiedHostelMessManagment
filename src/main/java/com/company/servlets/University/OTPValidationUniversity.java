package com.company.servlets.University;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
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

/**
 * Servlet implementation class OTPValidationUniversity
 */
public class OTPValidationUniversity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OTPValidationUniversity() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to save university data and login credentials
	private boolean saveUniversity(String universityName, String universityEmail, Long mobile, String location,
			String password) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Save to University table
			University university = new University();
			university.setName(universityName);
			university.setUniversityEmail(universityEmail);
			university.setUniversityMobile(mobile);
			university.setLocation(location);

			// Save to LoginPassword table
			LoginPassword loginPassword = new LoginPassword();
			loginPassword.setEmail(universityEmail); // Use university email as login email
			loginPassword.setPassword(password);
			loginPassword.setCategory("University");
			loginPassword.setMobileNo(mobile);

			// Save objects to the database
			session.save(university);
			session.save(loginPassword);

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if registration is successful
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if registration fails
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String enteredOTP = request.getParameter("otp");
		String universityName = request.getParameter("university_name");
		String universityEmail = request.getParameter("university_email");
		String mob = request.getParameter("university_mobile");
		String location = request.getParameter("location");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		Long mobile = Long.parseLong(mob);

		// Retrieve stored OTP from session
		HttpSession session = request.getSession();
//		String storedOTP = (String) session.getAttribute("otpStored");
		String storedOTP = request.getParameter("otpStored");

		String storedEmail = (String) session.getAttribute("universityEmail");

		String sessionId = session.getId();
		System.out.println(sessionId);

		PrintWriter out = response.getWriter();

		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			out.println("Password is Mismathch!!");
			return;
		}

//		System.out.println(enteredOTP + " -- " + storedOTP + " " + universityEmail + " " + storedEmail);
		
		
		enteredOTP = "";
		storedOTP = "";
		
		if (enteredOTP.equals(storedOTP)) {

			// Save to database
			boolean success = saveUniversity(universityName, universityEmail, mobile, location, password);

			if (success) {
				out.println("Successfully Registered"); // Redirect to login page
			} else {
				out.println("Something Wend Wrong!!");
				
			}

		} else {
			out.println("Wrong OTP Entered!! Or Wrong Information Filled");
		}
	}

}
