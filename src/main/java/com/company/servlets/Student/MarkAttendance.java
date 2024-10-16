package com.company.servlets.Student;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
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
import org.hibernate.query.Query;

import com.company.entities.Attendance;
import com.company.entities.OptOut;
import com.company.entities.Student;
import com.company.helper.AttendanceIdGenerator;
import com.company.helper.JsonConverter;

/**
 * Servlet implementation class MarkAttendance
 */
public class MarkAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MarkAttendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	private static String getTime() {
		LocalTime currentTime = LocalTime.now();

		// Define the cut-off time
		LocalTime cutOffTime = LocalTime.of(16, 0); // 4:00 PM

		// Check if the current time is before or after the cut-off time
		if (currentTime.isBefore(cutOffTime)) {
			System.out.println("Morning");
			return "Morning";

		} else {
			System.out.println("Evening");
			return "Evening";
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nfcId = request.getParameter("nfcId");
		String time = request.getParameter("time");

		HttpSession s = request.getSession();
//		String studentId = (String) session.getAttribute("studentId");
//		String nfcId = (String) session.getAttribute("nfcId");
//		String studentName = (String) session.getAttribute("studentName");

		// Create Hibernate session
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {

			// Check if the student with the given NFC ID exists
			String studentHql = "FROM Student WHERE nfcId = :nfcId";

			Query<Student> studentQuery = session.createQuery(studentHql, Student.class);

			studentQuery.setParameter("nfcId", nfcId);

			List<Student> students = studentQuery.list();
			System.out.println("Hello");
			if (students.isEmpty()) {
				System.out.println("Hello this is not exist !!");
//				response.getWriter().write("Student not found with the given NFC ID.");
				response.setStatus(402);
				return;
			}
			System.out.println("Hello this is after the !!");
			Student student = students.get(0);
			time = getTime();

			// Check if the student has opted out
			String optOutHql = "FROM OptOut WHERE studentId = :studentId AND date = :currentDate AND time = :time";
			Query<OptOut> optOutQuery = session.createQuery(optOutHql, OptOut.class);
			optOutQuery.setParameter("studentId", student.getRollNo());
			optOutQuery.setParameter("currentDate", new Date());
			optOutQuery.setParameter("time", time);
			List<OptOut> optOuts = optOutQuery.list();

			if (!optOuts.isEmpty()) {
//				response.getWriter().write("Student has opted out.");
				response.setStatus(409);
				return;
			}

			Attendance attendance = new Attendance();

			String attendaceId = new AttendanceIdGenerator().attendanceId(nfcId, new Date(), time);

			attendance.setNfcId(nfcId);
			attendance.setAttendanceId(attendaceId);
			attendance.setStudenId(student.getRollNo());
			attendance.setDate(new Date());
			attendance.setStatus("Present");
			attendance.setMessId(student.getMesh_Id());
			attendance.setStudnetName(student.getFullName());
			attendance.setTime(time);
			session.save(attendance);
			session.getTransaction().commit();

			JsonConverter jc = new JsonConverter();
			response.getWriter().write(jc.StudentObjectToJson(students));
			// Send student details back to the client
			response.setContentType("application/json");
			response.setStatus(200);
			System.out.println("Hello");
//			response.getWriter().write(studentToJson(student));
		} catch (Exception e) {
			response.setStatus(401);

			e.printStackTrace();
		}

	}

}
