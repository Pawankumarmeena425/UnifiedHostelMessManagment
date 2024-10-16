package com.company.servlets.Warden;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.company.entities.Warden;

/**
 * Servlet implementation class OTPValidationWarden
 */
public class OTPValidationWarden extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OTPValidationWarden() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to add warden's ID and password to login_password table
	private boolean addCredentialsToLoginPassword(String email, String password) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Create new LoginPassword entity
			LoginPassword loginPassword = new LoginPassword();
			loginPassword.setCategory("Warden");
			loginPassword.setEmail(email);
			loginPassword.setPassword(password);

			// Save login password to the database
			session.save(loginPassword);

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if login password is added successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs
		}
	}

	private boolean checkWardenExistence(String email) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Create HQL query to check if warden exists
			String hql = "FROM Warden WHERE email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			List<Warden> wardens = query.getResultList();

			session.close();

			return !wardens.isEmpty(); // Return true if warden exists, false otherwise
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false in case of an error
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String enteredOTP = request.getParameter("otp");

		// Retrieve stored OTP from session
		HttpSession session = request.getSession();
		String storedOTP = (String) session.getAttribute("otp");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");

		boolean wardenExists = checkWardenExistence(email);

		PrintWriter out = response.getWriter();
		// Validate OTP
		if (enteredOTP.equals(storedOTP) && wardenExists) {

			// OTP matched, add student's ID and password to login_password table
			boolean success = addCredentialsToLoginPassword(email, password);

			if (success) {
				session.removeAttribute("otp");
				out.println("SingUp Successfully!!");
			} else {
				out.println("Duplicate Entry , user already SignUp!");

			}
		} else {
			out.println("Wrong OTP Entered!! Or Wrong Information Filled");
		}

	}

}
