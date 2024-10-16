package com.company.servlets;

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

import com.company.entities.Manager;
import com.company.entities.Mess;
import com.company.entities.Warden;
import com.company.helper.JsonConverter;
import com.company.helper.MessIdGenerator;

import jakarta.persistence.Query;

/**
 * Servlet implementation class EditMess
 */
public class EditMess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditMess() {
		super();

	}

	// Method to update mess details in the database
	private boolean updateMess(String messId, String messNo, String wardenName, String wardenEmail, String managerName,
			String managerEmail, String messLocation, int allowableOptOut) {
		try {

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Fetch existing mess details
			Mess mess = session.get(Mess.class, messId);
			// Update mess details
			mess.setLocation(messLocation);
			mess.setAllowable_optout(allowableOptOut);
			session.update(mess);

			Warden warden = session.get(Warden.class, wardenEmail);
			if (warden != null) {
				warden.setName(wardenName);
			} else {
				System.out.println("Warden Yet Not Registered");
			}

			Manager manager = session.get(Manager.class, managerEmail);
			if (manager != null) {
				manager.setName(managerName);
			} else {
				System.out.println("Manager Yet Not Registered");
			}

			tx.commit();
			session.close();

			return true; // Return true if mess is updated successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs while updating mess
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve mess ID from request parameter
		int messNo = Integer.parseInt(request.getParameter("mess_no"));

		HttpSession sess = request.getSession();
		String universityName = (String) sess.getAttribute("university_name");

		MessIdGenerator messIdGen = new MessIdGenerator();
		String messId = messIdGen.messId(universityName, messNo);

		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Fetch mess by ID
			Mess mess = session.get(Mess.class, messId);

			String mess_id = mess.getMessId();
			String uniId = mess.getUniversityId();
			String wardenId = mess.getWardenId();
			int allowable_optout = mess.getAllowable_optout();
			String location = mess.getLocation();
			String managerId = mess.getManagerId();

			JsonConverter js = new JsonConverter();

			PrintWriter out = response.getWriter();
			out.println(js.MessObjectToJson(mess));

			session.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		String messId = request.getParameter("mess_id");
		String messNo = request.getParameter("mess_no");
		String wardenName = request.getParameter("warden_name");
		String wardenEmail = request.getParameter("warden_email");
		String managerName = request.getParameter("manager_name");
		String managerEmail = request.getParameter("manager_email");
		String messLocation = request.getParameter("mess_location");
		int allowableOptOut = Integer.parseInt(request.getParameter("allowable_optout"));

		// Update mess details in the database
		boolean success = updateMess(messId, messNo, wardenName, wardenEmail, managerName, managerEmail, messLocation,
				allowableOptOut);

		PrintWriter out = response.getWriter();

		if (success) {
			out.println("success");
		} else {
			out.println("Error Occured");
		}
	}

}
