package com.company.servlets.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mysql.cj.Query;

/**
 * Servlet implementation class FetchOptOut
 */
public class FetchOptOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FetchOptOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Map<String, Integer> featchOptOutDetails(String studentId) {

		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Get the current year
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);

			// Create a map to store the count of opt-outs per month
			Map<String, Integer> optOutCountPerMonth = new LinkedHashMap<String, Integer>();

			// Initialize the map with month names
			String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August",
					"September", "October", "November", "December" };
			for (String month : months) {
				optOutCountPerMonth.put(month, 0);
			}

			// Fetch the opt-out counts for each month
			String hql = "SELECT MONTH(o.date), COUNT(o.id) FROM OptOut o WHERE o.studentId = :studentId AND YEAR(o.date) = :currentYear GROUP BY MONTH(o.date)";
			jakarta.persistence.Query query = session.createQuery(hql);
			query.setParameter("studentId", studentId);
			query.setParameter("currentYear", currentYear);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Integer month = (Integer) result[0];
				Long count = (Long) result[1];
				optOutCountPerMonth.put(months[month - 1], count.intValue());
			}

//			jsonResponse.put("optOutCountPerMonth", optOutCountPerMonth);
			tx.commit();

			return optOutCountPerMonth;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String studentId = (String) session.getAttribute("studentId");
		studentId = "DSFDSDDdjlkD";

		PrintWriter out = response.getWriter();
		Map<String, Integer> optOutCountPerMonth = featchOptOutDetails(studentId);
		if (optOutCountPerMonth != null) {
			out.println("OptOut Data Fetch Successfully!!");
			for (String key : optOutCountPerMonth.keySet()) {
				out.println(key + "-->>" + optOutCountPerMonth.get(key));

			}

		}

	}
}
