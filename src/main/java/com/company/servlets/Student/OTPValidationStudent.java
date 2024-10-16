package com.company.servlets.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.company.entities.Student;

/**
 * Servlet implementation class OTPValidationStudent
 */
public class OTPValidationStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OTPValidationStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to add student's ID and password to login_password table
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
			loginPassword.setCategory("Student");
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

	private boolean checkStudentExistence(String rollNo, java.util.Date dob, String email) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Create HQL query to check if student exists
//			String hql = "FROM Student WHERE rollNo = :rollNo AND dob = :dob AND email = :email";
			String hql = "FROM Student WHERE rollNo = :rollNo  AND email = :email";

			Query query = session.createQuery(hql);
			query.setParameter("rollNo", rollNo);
//			query.setParameter("dob", dob);
			query.setParameter("email", email);
			List<Student> students = query.getResultList();

			session.close();

			return !students.isEmpty(); // Return true if student exists, false otherwise
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false in case of an error
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String enteredOTP = request.getParameter("otp");

		HttpSession session = request.getSession();
		String storedOTP = (String) session.getAttribute("otp");
		String rollNo = request.getParameter("roll_no");
		String dob = request.getParameter("dob");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateOfBirth = null;
		try {
			dateOfBirth = formatter1.parse(dob);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Check if student exists in the database
		boolean studentExists = checkStudentExistence(rollNo, dateOfBirth, email);

		studentExists = true;
		PrintWriter out = response.getWriter();
		enteredOTP = "";
		storedOTP = "";
		// Validate OTP
		if (enteredOTP.equals(storedOTP) && studentExists) {

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
