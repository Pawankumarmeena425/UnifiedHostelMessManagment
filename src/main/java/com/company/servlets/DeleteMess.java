package com.company.servlets;

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
import org.hibernate.query.Query;

import com.company.entities.Mess;

/**
 * Servlet implementation class DeleteMess
 */
public class DeleteMess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteMess() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Method to delete mess and its related entities
	private boolean deleteMess(String messId) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Fetch mess by ID
			Mess mess = session.get(Mess.class, messId);

			if (mess != null) {

				String hql = "DELETE FROM Warden WHERE  messId = :messId";
				Query query = session.createQuery(hql);
				query.setParameter("messId", messId);
				int deletedCount = query.executeUpdate();

				
				String hqlManager = "DELETE FROM Manager WHERE messId = :messId";
				Query queryManager = session.createQuery(hqlManager);
				queryManager.setParameter("messId", messId);
				int deletedCountMan = queryManager.executeUpdate();

//				System.out.println(deletedCount + " " + deletedCountMan);

				session.delete(mess);
			} else {
				// If mess not found, return false
				return false;
			}

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if mess is deleted successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs while deleting mess
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve mess ID from request parameter
		String messId = request.getParameter("mess_id");

		// Delete mess and its related entities
		boolean success = deleteMess(messId);
		PrintWriter out = response.getWriter();

		if (success) {
			out.println("Successs"); // Redirect to success page
		} else {
			out.println("Not possible to delete!!"); // Redirect back to delete mess page with error
														// parameter
		}
	}

}
