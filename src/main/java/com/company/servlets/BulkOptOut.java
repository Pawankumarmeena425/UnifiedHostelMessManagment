package com.company.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.company.entities.OptOut;

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

/**
 * Servlet implementation class BulkAttendance
 */
public class BulkOptOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BulkOptOut() {
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

	private List<OptOut> fetchOptOutData(Date startDate, Date endDate) {
		// Create Hibernate session
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		String hql = "FROM OptOut o WHERE o.date BETWEEN :startDate AND :endDate";
		Query<OptOut> query = session.createQuery(hql, OptOut.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		List<OptOut> result = query.list();

		session.getTransaction().commit();
		session.close();

		return result;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String startMonthStr = request.getParameter("startMonth");
		String endMonthStr = request.getParameter("endMonth");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date startDate = null;
		Date endDate = null;

		try {
			startDate = sdf.parse(startMonthStr);
			endDate = sdf.parse(endMonthStr);
		} catch (ParseException e) {
			throw new ServletException("Invalid date format. Please use 'yyyy-MM'.", e);
		}

		List<OptOut> optOutList = fetchOptOutData(startDate, endDate);
		System.out.print(optOutList.size());

//		Workbook workbook = new XSSFWorkbook();
//		Sheet sheet = workbook.createSheet("OptOut Data");
//
//		// Create header row
//		Row headerRow = sheet.createRow(0);
//		headerRow.createCell(0).setCellValue("ID");
//		headerRow.createCell(1).setCellValue("Student ID");
//		headerRow.createCell(2).setCellValue("Date");
//		headerRow.createCell(3).setCellValue("Time");
//		headerRow.createCell(4).setCellValue("Reason");
//
//		// Fill data rows
//		int rowIndex = 1;
//		for (OptOut optOut : optOutList) {
//			Row row = sheet.createRow(rowIndex++);
//			row.createCell(0).setCellValue(optOut.getId());
//			row.createCell(1).setCellValue(optOut.getStudentId());
//			row.createCell(2).setCellValue(optOut.getDate().toString());
//			row.createCell(3).setCellValue(optOut.getTime());
//			row.createCell(4).setCellValue(optOut.getReason());
//		}
//
//		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//		response.setHeader("Content-Disposition", "attachment; filename=opt_out_data.xlsx");
//
//		try (OutputStream out = response.getOutputStream()) {
//			workbook.write(out);
//		} finally {
//			workbook.close();
//		}
	}

}
