package com.company.servlets.Student;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.query.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Feedback;
import com.company.entities.Student;
import com.mysql.cj.Query;

/**
 * Servlet implementation class GetFeedback
 */
public class GetFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetFeedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Feedback> getFeedbackForLast7Days() {

		try {

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -7);
			Date sevenDaysAgo = calendar.getTime();

			String hql = "FROM Feedback f WHERE f.date >= :sevenDaysAgo";

			org.hibernate.query.Query query = session.createQuery(hql);

			query.setParameter("sevenDaysAgo", sevenDaysAgo);

//			List results = query.list();

			List<Feedback> feedbackList = query.getResultList();

			session.getTransaction().commit();
			session.close();

			return feedbackList;

		} catch (Exception e) {
			e.printStackTrace();
			return null; // Return null if an error occurs
		}

	}

	public void byteToImage(byte[] imageData, String imageName) throws IOException {

		String folderPath = "C:\\Users\\Pawan ji\\Downloads\\ExtrectedImage\\";
		String imagePath = folderPath + imageName;
		OutputStream outputstream = new FileOutputStream(imagePath);
		outputstream.write(imageData);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Feedback> feedbackList = getFeedbackForLast7Days();

		System.out.println(feedbackList.size());
		System.out.println("Success!!");
		for (Feedback fd : feedbackList) {
			System.out.println(fd.getDate());
			System.out.println(fd.getFeedbackId());
			byteToImage(fd.getPhoto(), fd.getFeedbackId());
			for (int i = 0; i < fd.getPhoto().length; i++) {
				System.out.print(fd.getPhoto()[i]);
			}

		}
	}

}
