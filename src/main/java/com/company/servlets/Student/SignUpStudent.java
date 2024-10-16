package com.company.servlets.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.company.entities.Student;
import com.company.helper.OTPAuthentaction;

/**
 * Servlet implementation class SignUpStudent
 */
public class SignUpStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to check if a student exists in the Student table
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
			String hql = "FROM Student WHERE rollNo = :rollNo AND email = :email";

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

	// Method to generate a random OTP
	private int generateOTP() {
		Random random = new Random();
		return 100000 + random.nextInt(900000); // Generate a 6-digit OTP
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
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
		dateOfBirth = null;
		PrintWriter out = response.getWriter();

		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			out.println("Password is Mismathch!!");
			return;
		}

		// Check if student exists in the database
		boolean studentExists = checkStudentExistence(rollNo, dateOfBirth, email);

		if (studentExists) {

			// Generate OTP
			int otp = generateOTP();

			// Send OTP to the user (not implemented)
			// Store OTP in session
			HttpSession session = request.getSession();
			session.setAttribute("otp", otp + "");
			OTPAuthentaction otpAut = new OTPAuthentaction();
			otpAut.otpSender(email, otp + "");
			response.setStatus(200);
//			out.println("OTP Send Successfully!!");
			out.println(otp);
			return;

		} else {
			response.setStatus(404);
			out.println("Student is not Register Yet , Please contact to the University or Warden!!");
			return;
		}
	}

}
