package com.company.servlets.Manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.company.entities.LoginPassword;
import com.company.entities.Manager;
import com.company.helper.OTPAuthentaction;

/**
 * Servlet implementation class SingUpManager
 */
public class SingUpManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SingUpManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to check if a manager exists in the Manager table
	private boolean checkManagerExistence(String email) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Create HQL query to check if manager exists
			String hql = "FROM Manager WHERE email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			List<Manager> managers = query.getResultList();

			session.close();

			return !managers.isEmpty(); // Return true if manager exists, false otherwise
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false in case of an error
		}
	}

	private int generateOTP() {
		Random random = new Random();
		return 100000 + random.nextInt(900000); // Generate a 6-digit OTP
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Retrieve form data
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");

		PrintWriter out = response.getWriter();

		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			out.println("Password Mismatch!!");// Redirect back to signup page with // error parameter
			return;
		}

		// Check if warden exists in the database
		boolean managerExists = checkManagerExistence(email);

		if (managerExists) {
			// Generate OTP
			int otp = generateOTP();

			// Send OTP to the user (not implemented)
			// Store OTP in session
			HttpSession session = request.getSession();
			session.setAttribute("otp", otp + "");
			OTPAuthentaction otpAut = new OTPAuthentaction();
			otpAut.otpSender(email, otp + "");

			out.println("OTP Send Successfully!!");
		} else {
			out.println("Manager Not yet Registered , Please contact to the University!!");
		}
	}

}
