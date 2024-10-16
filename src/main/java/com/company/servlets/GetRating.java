package com.company.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.ListOfMeal;
import com.company.entities.Meal;

import jakarta.persistence.Query;

/**
 * Servlet implementation class GetRating
 */
public class GetRating extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetRating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double previousDayAverageRating(String messId) {

		try {

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Calculate previous day's average rating
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			String previousDayStr = sdf.format(new Date(currentDate.getTime() - (1000 * 60 * 60 * 24)));

			String hqlPreviousDay = "SELECT AVG(r.rating) FROM Rating r WHERE r.messId = :messId AND DATE(r.date) = :previousDay";
			Query queryPreviousDay = session.createQuery(hqlPreviousDay);

			queryPreviousDay.setParameter("messId", messId);
			queryPreviousDay.setParameter("previousDay", sdf.parse(previousDayStr));

			List previousDayAverageRating = queryPreviousDay.getResultList();
			tx.commit();

			return (Double) previousDayAverageRating.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0.0;

	}

	public Double currentAverageRating(String messId) {

		try {

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Calculate current average rating
			String hqlCurrent = "SELECT AVG(r.rating) FROM Rating r WHERE r.messId = :messId";

			Query q = session.createQuery(hqlCurrent);

			q.setParameter("messId", messId);
			List currentAverageRating = q.getResultList();
			System.out.println(currentAverageRating);
			tx.commit();
			return (Double) currentAverageRating.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String messId = (String) session.getAttribute("messId");

		messId = "MBMUIJ324";

		Double currentAverangeRating = currentAverageRating(messId);
		Double prevDayAvgRating = previousDayAverageRating(messId);
		Double arr[] = new Double[2];
		arr[0] = currentAverangeRating;
		arr[1] = currentAverangeRating;
		PrintWriter out = response.getWriter();

		response.setContentType("application/json");

	}

}
