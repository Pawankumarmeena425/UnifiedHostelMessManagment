package com.company.servlets.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Feedback;
import com.company.entities.FeedbackPhoto;
import com.company.entities.Rating;
import com.company.helper.FeedbackIdGenerator;

/**
 * Servlet implementation class StoreFeedback
 */

@MultipartConfig
public class StoreFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StoreFeedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean storeFeedback(String feedbackId, String studentId, String messId, Date date, String time,
			String text, byte[] imageData) {

		try {

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

//			Feedback feedback = new Feedback(feedbackId, date, messId, studentId, time, text);
			Feedback feedback = new Feedback();
			feedback.setFeedbackId(feedbackId);
			feedback.setDate(date);
			feedback.setStudentId(studentId);
			feedback.setMessId(messId);
			feedback.setTime(time);
			feedback.setText(text);
			feedback.setPhoto(imageData);

			session.save(feedback);
			tx.commit();
			session.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean processRequest(HttpServletRequest request, HttpServletResponse response, String feedbackId,
			String studentId, String messId, Date date, String time, String text) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Part part = request.getPart("file");
		String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		InputStream fileContent = part.getInputStream();

		byte[] imageData = new byte[fileContent.available()];
		fileContent.read(imageData);

		boolean success = storeFeedback(feedbackId, studentId, messId, date, time, text, imageData);

		if (success) {
			return true;
		} else {
			return false;

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String studentId = (String) session.getAttribute("studentId");
		String messId = (String) session.getAttribute("messId");

		String dateString = request.getParameter("date");
		String time = request.getParameter("time");
		String text = request.getParameter("feedbackText");
		Date date = new Date();
		String feedbackId = null;

		try {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//			Date dateFormated = dateFormat.parse(dateString);
//			feedbackId = new FeedbackIdGenerator().feedbackId(studentId, dateFormated, time);
			feedbackId = "dgdgdgdfgsdfgsdfg";
//			 Store feedback entry

//	

		} catch (Exception e) {
			response.getWriter().println("Error: Invalid date format.");
		}

		boolean success = processRequest(request, response, feedbackId, studentId, messId, date, time, text);

		if (success) {
			response.getWriter().println("Feedback stored successfully.");

		} else {
			response.getWriter().println("Something Went Wrong!!.");

		}
	}

}
