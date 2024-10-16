package com.company.servlets.Student;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class DeleteOneStudent
 */
public class DeleteOneStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteOneStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to delete student from the database
	private boolean deleteStudent(String rollNo) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Fetch student by roll number
			Student student = session.get(Student.class, rollNo);

			if (student != null) {
				// Delete student
				session.delete(student);
			} else {
				// If student not found, return false
				return false;
			}

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if student is deleted successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs while deleting student
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve roll number from request parameter
		String rollNo = request.getParameter("roll_no");

		// Delete student from the database
		boolean success = deleteStudent(rollNo);

		PrintWriter out = response.getWriter();

		if (success) {
			out.println("Student Delte Succesfuly!!"); // Redirect to success page
		} else {
			out.println("Not Possible to delete");
		}
	}

}
