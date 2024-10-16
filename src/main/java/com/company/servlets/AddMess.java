package com.company.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.company.helper.MessIdGenerator;

public class AddMess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddMess() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean saveMess(int messNo, String messId, String universityId, String wardenName, String wardenEmail,
			String managerName, String managerEmail, String messLocation, int allowableOptOut) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Save to Mess table
			Mess mess = new Mess();

			mess.setMessNo(messNo);
			mess.setMessId(messId);
			mess.setUniversityId(universityId);
			mess.setWardenId(wardenEmail);
			mess.setManagerId(managerEmail);
			mess.setLocation(messLocation);
			mess.setAllowable_optout(allowableOptOut);
			session.save(mess);

			// Save to Warden table
			Warden warden = new Warden();
			warden.setName(wardenName);
			warden.setEmail(wardenEmail);
			warden.setMessId(messId);
			warden.setUniversityId(universityId);
			session.save(warden);

			// Save to Manager table
			Manager manager = new Manager();
			manager.setName(managerName);
			manager.setEmail(managerEmail);
			manager.setMessId(messId);
			manager.setUniversityId(universityId);

			// Save objects to the database

			session.save(manager);

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if mess is saved successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs while saving mess
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data
		int messNo = Integer.parseInt(request.getParameter("mess_no"));
		String universityId = request.getParameter("university_email");
		String wardenName = request.getParameter("warden_name");
		String wardenEmail = request.getParameter("warden_email");
		String managerName = request.getParameter("manager_name");
		String managerEmail = request.getParameter("manager_email");
		String messLocation = request.getParameter("mess_location");
		int allowableOptOut = Integer.parseInt(request.getParameter("allowable_optout"));

		HttpSession session = request.getSession();
//		String universityId = (String) session.getAttribute("university_email");
		String universityName = (String) session.getAttribute("university_name");

		universityName = "MBM University";
		MessIdGenerator messIdGen = new MessIdGenerator();
		String messId = messIdGen.messId(universityName, messNo);

		if (universityId == null) {
			// Logout servlet

		}

		// Save mess to database
		boolean success = saveMess(messNo, messId, universityId, wardenName, wardenEmail, managerName, managerEmail,
				messLocation, allowableOptOut);

		PrintWriter out = response.getWriter();
		out.print("okey");

		if (success) {
			out.println("Successfully Add Mess!!");// Redirect to success page
		} else {
			out.println("Error Occured!!"); // Redirect back to add mess page with error parameter
		}

	}

}
