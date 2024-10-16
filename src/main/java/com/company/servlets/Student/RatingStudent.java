package com.company.servlets.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Rating;
import com.company.helper.RatingIdGenerator;

/**
 * Servlet implementation class RatingStudent
 */
public class RatingStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RatingStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean storeRating(String ratingId, String studentId, String messId, Date date, String time,
			int ratingValue) {
		try {

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

//			// Create a new Rating object
			Rating rating = new Rating(ratingId, studentId, messId, date, time, ratingValue);

			session.save(rating);
			tx.commit();
			session.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String studentId = (String) session.getAttribute("studentId");

		String messId = (String) session.getAttribute("messId");

		studentId = "21UCSE4rw321";
		messId = "MBMUIJ324";

		String dateStr = request.getParameter("date");
		String time = request.getParameter("time");
		int ratingValue = Integer.parseInt(request.getParameter("rating"));

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = dateFormat.parse(dateStr);

			String ratingId = new RatingIdGenerator().ratingId(studentId, date, time);

			// Store rating in the database
			boolean success = storeRating(ratingId, studentId, messId, date, time, ratingValue);

			PrintWriter out = response.getWriter();
			if (success) {
				out.println("Successfully Rated!!");
			} else {
				out.println("Not possible to Rate!!");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
