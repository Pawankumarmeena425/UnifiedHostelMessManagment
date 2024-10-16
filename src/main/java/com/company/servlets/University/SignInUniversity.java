package com.company.servlets.University;

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

import com.company.entities.LoginPassword;
import javax.servlet.http.HttpSession;

import jakarta.persistence.Query;

/**
 * Servlet implementation class SignInUniversity
 */
public class SignInUniversity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignInUniversity() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to authenticate university login
	private boolean authenticateUniversity(String universityEmail, String password) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Query to check if the provided credentials exist in the database
			Query query = session.createQuery("FROM LoginPassword WHERE email = :email AND password = :password");

			query.setParameter("email", universityEmail);
			query.setParameter("password", password);
			LoginPassword loginPassword = (LoginPassword) ((org.hibernate.query.Query) query).uniqueResult();

			session.close();

			return loginPassword != null; // Return true if credentials exist
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs during authentication
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String universityEmail = request.getParameter("university_email");
		String password = request.getParameter("password");

		// Validate login credentials
		boolean isAuthenticated = authenticateUniversity(universityEmail, password);

		PrintWriter out = response.getWriter();

		if (isAuthenticated) {

			HttpSession session = request.getSession();
			session.setAttribute("university_email", universityEmail);

			String name = (String) session.getAttribute("university_email");
			out.println(name);
			response.setStatus(200);
			out.println("Sussessfully Login");

		} else {
			response.setStatus(401);
			out.println("Invalid Creditionals");
		}
	}

}
