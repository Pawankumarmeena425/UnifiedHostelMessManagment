package com.company.servlets.University;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Warden;
import com.company.helper.JsonConverter;

import jakarta.persistence.Query;

/**
 * Servlet implementation class GetMessDetails
 */
public class GetMessDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMessDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String universityId = request.getParameter("university_email");

		// Create Hibernate session
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		String hql = "FROM Warden w WHERE w.universityId = :universityId";
		Query query = session.createQuery(hql);
		query.setParameter("universityId", universityId);

		List<Warden> details = query.getResultList();

		JsonConverter jc = new JsonConverter();

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");

		response.setContentType("application/json");
		response.getWriter().write(jc.WardenObjectToJson(details));

	}

}
