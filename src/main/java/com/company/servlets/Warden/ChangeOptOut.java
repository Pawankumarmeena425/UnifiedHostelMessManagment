package com.company.servlets.Warden;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.company.entities.Mess;

import jakarta.persistence.Query;

/**
 * Servlet implementation class ChangeOptOut
 */
public class ChangeOptOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChangeOptOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int getCurrentOptOut(String messId) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();

			// Create HQL query to fetch current allowable opt-out
			String hql = "SELECT m.allowable_optout FROM Mess m WHERE m.messId = :messId";
			Query query = session.createQuery(hql);
			query.setParameter("messId", messId);
			List<Integer> results = query.getResultList();

			session.close();

			if (!results.isEmpty()) {
				return results.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0; // Return 0 if unable to fetch current opt-out
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String messId = (String) session.getAttribute("messId");
		messId = "MBMUJ6";

		int currentOptOut = getCurrentOptOut(messId);

		PrintWriter out = response.getWriter();
		out.println("Current OptOut : " + currentOptOut);

	}

	private boolean updateOptOut(String messId, int newOptOut) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Fetch the mess entity
			Mess mess = session.get(Mess.class, messId);
			if (mess != null) {

				mess.setAllowable_optout(newOptOut);

				// Save the updated entity
				session.update(mess);

				tx.commit();

				session.close();

				return true; // Return true if opt-out is updated successfully
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // Return false if unable to update opt-out
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String messId = (String) session.getAttribute("messId");
		messId = "MBMUJ6";

		// Retrieve new opt-out value from form
		int newOptOut = Integer.parseInt(request.getParameter("newOptOut"));

		// Update opt-out in the mess table
		boolean success = updateOptOut(messId, newOptOut);

		PrintWriter out = response.getWriter();

		if (success) {
			out.println("Successfully Updated!!");
		} else {
			out.println("Something Went Wrong!!");
		}

	}

}
