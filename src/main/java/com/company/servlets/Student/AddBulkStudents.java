package com.company.servlets.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Student;
import com.company.helper.MessIdGenerator;

/**
 * Servlet implementation class AddBulkStudents
 */

@MultipartConfig
public class AddBulkStudents extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBulkStudents() {
		super();
		// TODO Auto-generated constructor stub
	}

	String uniName = null;
	String universityId = null;

	// Method to add student to the database
	private boolean addStudent(String fullName, String rollNo, java.util.Date dob, String nfcId, String branch,
			Long mobile, String email, String messNo, String meshId, String universityId) {
		try {
			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			// Create Student entity
			Student student = new Student();
			student.setFullName(fullName);
			student.setRollNo(rollNo);
			student.setDob(dob);
			student.setNfcId(nfcId);
			student.setBranch(branch);
			student.setMobileNo(mobile);
			student.setEmail(email);
			student.setUniversity_Id(universityId);
			student.setMesh_NO(messNo);
			student.setMesh_Id(meshId);

			// Save student to the database
			session.save(student);

			// Commit transaction
			tx.commit();
			session.close();

			return true; // Return true if student is added successfully
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Return false if an error occurs while adding student
		}
	}

	protected boolean addData(ArrayList<String> list) {

		String fullName = list.get(0);
		String rollNo = list.get(1);
		String dob = list.get(2);
		String nfcId = list.get(3);
		String mob = list.get(4);
		String email = list.get(5);
		String branch = list.get(6);
		String messNo = list.get(7);
		Long mobile = Long.parseLong(mob);
//		String meshId = new MessIdGenerator().messId(uniName, Integer.parseInt(messNo));
		String meshId = null;

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date1 = null;
		try {
			date1 = formatter1.parse(dob);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (universityId == null) {
			// Logout servlet

		}

		// Add student to the database
		boolean success = addStudent(fullName, rollNo, (java.util.Date) date1, nfcId, branch, mobile, email, messNo,
				meshId, universityId);

		if (success) {
			return true; // Redirect to success page
		} else {
			return false; // Redirect back to add student page with error
							// parameter
		}

	}

	protected void readFile(String path, String fileName) {

		String filePath = path + "/" + fileName;
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();
			while (sheets.hasNext()) {
				Sheet sh = sheets.next();
				System.out.println("Sheet Name is : " + sh.getSheetName());
				Iterator<Row> iterator = sh.iterator();

				ArrayList<String> list = new ArrayList<String>();

				while (iterator.hasNext()) {
					Row row = iterator.next();
					Iterator<Cell> cellIterator = row.iterator();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						list.add(cellValue);
						System.out.print(cellValue + '\t');
					}
					addData(list);
					list.clear();
					System.out.println();

				}

			}
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Part part = request.getPart("file");
		String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		InputStream fileContent = part.getInputStream();

		try {

			String path = getServletContext().getRealPath("") + "files";
			Path uploadPath = Paths.get(path);
			System.out.println(path);

			if (!Files.exists(uploadPath)) {

				Files.createDirectories(uploadPath);
			}
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
			readFile(path, fileName);

			response.getWriter().println("File" + fileName + "has been Uploaded!!");

			System.out.println("Upload Successfully !!");
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		uniName = (String) session.getAttribute("university_Name");

		universityId = (String) session.getAttribute("university_email");

		processRequest(request, response);
	}

}
