package com.company.servlets.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Student;

/**
 * Servlet implementation class AddSingleStudent
 */
public class AddSingleStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddSingleStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to add student to the database
	private boolean addStudent(String fullName, String rollNo, java.util.Date dob, String nfcId, String branch,
			Long mobile, String email, String messNo, String meshId, String universityId) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Create Student entity
			Student student = new Student();
			student.setFullName(fullName);
			student.setRollNo(rollNo);
			student.setDob(dob);
			student.setNfcId(nfcId);
			student.setBranch(branch);
			student.setMobileNo(mobile);
			student.setEmail(email);
			student.setUniversity_Id(universityId);
			student.setMesh_NO(messNo);
			student.setMesh_Id(meshId);

			// Save student to the database
			session.save(student);

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if student is added successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs while adding student
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String fullName = request.getParameter("full_name");
		String rollNo = request.getParameter("roll_no");
		String dob = request.getParameter("dob");
		String nfcId = request.getParameter("nfc_id");
		String branch = request.getParameter("branch");
		String mob = request.getParameter("student_mobile");
		Long mobile = Long.parseLong(mob);
		String email = request.getParameter("student_email");
		String messNo = request.getParameter("mess_no");
		String meshId = null;

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date1 = null;
		try {
			date1 = formatter1.parse(dob);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		String universityId = (String) session.getAttribute("university_email");

		if (universityId == null) {
			// Logout servlet

		}

		// Add student to the database
		boolean success = addStudent(fullName, rollNo, (java.util.Date) date1, nfcId, branch, mobile, email, messNo,
				meshId, universityId);

		PrintWriter out = response.getWriter();

		if (success) {
			out.println("Successfully Registered Student!!"); // Redirect to success page
		} else {
			out.println("Failed!!"); // Redirect back to add student page with error
										// parameter
		}
	}

}
