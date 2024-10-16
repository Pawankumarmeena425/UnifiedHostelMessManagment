package com.company.servlets.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Student;

/**
 * Servlet implementation class EditStudent
 */
public class EditStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to fetch student details from the database
	private Student fetchStudentDetails(String rollNo) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Fetch student by roll number
			Student student = session.get(Student.class, rollNo);

			session.close();

			return student; // Return student details
		} catch (Exception e) {
			e.printStackTrace();
			return null; // Return null if an error occurs
		}
	}

	// Method to update student details in the database
	private boolean updateStudent(String rollNo, String fullName, java.util.Date dob, String nfcId, String branch,
			Long mobile, String email, String messNo) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Fetch existing student details
			Student student = session.get(Student.class, rollNo);

			// Update student details
			student.setFullName(fullName);
			student.setDob(dob);
			student.setBranch(branch);
			student.setMobileNo(mobile);
			student.setEmail(email);
			student.setMesh_NO(messNo);
			student.setMesh_Id(messNo);
			

			// Save changes to the database
			session.update(student);

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if student is updated successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs while updating student
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve roll number from request parameter
		String rollNo = request.getParameter("roll_no");

		// Fetch student details from the database
		Student student = fetchStudentDetails(rollNo);
		PrintWriter out = response.getWriter();
		out.println(student.getRollNo());
		out.println(student.getBranch());
		out.println(student.getEmail());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String rollNo = request.getParameter("roll_no");
		String fullName = request.getParameter("full_name");
		String dob = request.getParameter("dob");
		String nfcId = request.getParameter("nfc_id");
		String branch = request.getParameter("branch");
		String mob = request.getParameter("mobile");
		Long mobile = Long.parseLong(mob);
		String email = request.getParameter("email");
		String messNo = request.getParameter("mess_no");

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date1 = null;
		try {
			date1 = formatter1.parse(dob);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Update student details in the database
		boolean success = updateStudent(rollNo, fullName, date1, nfcId, branch, mobile, email, messNo);
		PrintWriter out = response.getWriter();

		if (success) {
			out.println("Successfully Edited");
		} else {
			out.println("NOt Possible to Edite"); // Redirect back to edit student
																						// page with error parameter
		}
	}

}
