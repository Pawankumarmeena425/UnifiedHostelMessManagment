package com.company.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.company.entities.OptOut;

/**
 * Servlet implementation class OptOutDownload
 */
public class OptOutDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OptOutDownload() {
		super();
		// TODO Auto-generated constructor stub
	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

	private List<OptOut> fetchOptOutData(Date startDate, Date endDate, String messId) {
		// Create Hibernate session
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		String hql = "FROM OptOut o WHERE o.messId = :messId AND o.date BETWEEN :startDate AND :endDate";

//		String hql = "SELECT o, s.fullName FROM OptOut o JOIN Student s ON o.studentId = s.rollNo "
//				+ "WHERE o.messId = :messId AND o.date BETWEEN :startDate AND :endDate";

		Query<OptOut> query = session.createQuery(hql, OptOut.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("messId", messId);

		List<OptOut> result = query.list();

		session.getTransaction().commit();
		session.close();

		return result;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String startMonthStr = request.getParameter("startMonth");
		String endMonthStr = request.getParameter("endMonth");

		HttpSession session = request.getSession();
//		String messId = (String) session.getAttribute("messId");
		String messId = (String) request.getParameter("messId");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date startDate = null;
		Date endDate = null;

		messId = "MBMUIJ324";

		try {
			startDate = sdf.parse(startMonthStr);
			endDate = sdf.parse(endMonthStr);
		} catch (ParseException e) {
			throw new ServletException("Invalid date format. Please use 'yyyy-MM'.", e);
		}

		List<OptOut> optOutList = fetchOptOutData(startDate, endDate, messId);
		System.out.print(optOutList.size());

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("OptOut Data");

		// Create header row
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Student ID");
		headerRow.createCell(2).setCellValue("Student Name");
		headerRow.createCell(3).setCellValue("Date");
		headerRow.createCell(4).setCellValue("Time");
		headerRow.createCell(5).setCellValue("Reason");
		headerRow.createCell(6).setCellValue("MessId");
//		headerRow.createCell(7).setCellValue("NFC ID");

		// Fill data rows
		int rowIndex = 1;
		for (OptOut optOut : optOutList) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(optOut.getId());
			row.createCell(1).setCellValue(optOut.getStudentId());
			row.createCell(2).setCellValue(optOut.getStudentName());
			row.createCell(3).setCellValue(optOut.getDate().toString());
			row.createCell(4).setCellValue(optOut.getTime());
			row.createCell(5).setCellValue(optOut.getReason());
			row.createCell(6).setCellValue(messId);
//			row.createCell(7).setCellValue(optOut.getNfcId());

		}

		for (Row row : sheet) // iteration over row using for each loop
		{
			for (Cell cell : row) // iteration over cell using for each loop
			{
				System.out.print(cell + "->");
			}
			System.out.println();
		}

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=attendance_data.xlsx");

		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
		} finally {
			workbook.close();
		}
	}

}
