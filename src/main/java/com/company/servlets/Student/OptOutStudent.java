package com.company.servlets.Student;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.company.entities.OptOut;
import com.company.helper.OptOutIdGenerator;

/**
 * Servlet implementation class OptOutStudent
 */
public class OptOutStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OptOutStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean storeOptOutEntry(OptOut optOut) {
		// Create Hibernate session
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {

			session.save(optOut);
			tx.commit();
			session.close();

			return true;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();

		}
		return false;
	}

	private boolean storeOptOutEntries(String studentId, String nfcId, String studentName, String messId,
			String fromDate, String toDate, String reason) {

		// Iterate over the date range and store opt-out entries for each date

		LocalDate start = LocalDate.parse(fromDate), end = LocalDate.parse(toDate);

		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {

//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//			Date currentDate = dateFormat.parse(date);

			Date currentDate = null;
			try {
				currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(currentDate);
			String optOutId = OptOutIdGenerator.optId(studentId, currentDate, "Morning");
			OptOut optOut = new OptOut(optOutId, studentId, nfcId, studentName, messId, currentDate, "Morning", reason);
			boolean flag = storeOptOutEntry(optOut);
			if (flag == false) {
				return false;
			}

			optOutId = OptOutIdGenerator.optId(studentId, currentDate, "Evening");
			optOut = new OptOut(optOutId, studentId, nfcId, studentName, messId, currentDate, "Evening", reason);
			flag = storeOptOutEntry(optOut);

			if (flag == false) {
				return false;
			}
			System.out.println(date);

		}
		return true;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
//		String studentId = (String) session.getAttribute("studentId");
//		String nfcId = (String) session.getAttribute("nfcId");
//		String studentName = (String) session.getAttribute("studentName");

//		String messId = (String) session.getAttribute("messId");
//		messId = "MBMUIJ324";
		String studentId = request.getParameter("studentId");
		String nfcId = request.getParameter("nfcId");
		String messId = request.getParameter("messId");
		String studentName = request.getParameter("studentName");
		String fromDateStr = request.getParameter("fromDate");
		String toDateStr = request.getParameter("toDate");
		String reason = request.getParameter("reason");

		try {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//			Date fromDate = dateFormat.parse(fromDateStr);
//			Date toDate = dateFormat.parse(toDateStr);

			System.out.println("Hello");
			// Iterate over the date range and store opt-out entries
			boolean success = storeOptOutEntries(studentId, nfcId, studentName, messId, fromDateStr, toDateStr, reason);

			if (success) {
				response.getWriter().println("Opt-out requests stored successfully.");
			} else {
				response.getWriter().println("Student Already Taken OptOut For the Given Time Range.");
			}

		} catch (Exception e) {
			e.printStackTrace();

			response.getWriter().println("Error: Invalid date format.");
		}
	}

}
