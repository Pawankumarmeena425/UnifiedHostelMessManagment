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
import com.company.helper.FactoryProvider;
import com.company.helper.JsonConverter;

/**
 * Servlet implementation class ViewStudent
 */
public class ViewStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to fetch student details from the database based on roll number
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve roll number from request parameter
		String rollNo = request.getParameter("roll_no");

		// Fetch student details from the database
		Student student = fetchStudentDetails(rollNo);

		PrintWriter out = response.getWriter();

		JsonConverter jc = new JsonConverter();
		out.println(jc.StudentObjectToJson(student));

//		// Set student details as request attribute
//		request.setAttribute("student", student);
//
//		// Forward request to viewStudent.jsp to display the student details
//		request.getRequestDispatcher("viewStudent.jsp").forward(request, response);
	}

}
