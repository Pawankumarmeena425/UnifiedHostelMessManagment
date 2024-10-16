package com.company.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.Manager;
import com.company.entities.Mess;
import com.company.entities.Warden;
import com.company.helper.MessIdGenerator;

/**
 * Servlet implementation class AddBulkMess
 */

@MultipartConfig
public class AddBulkMess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBulkMess() {
		super();
		// TODO Auto-generated constructor stub
	}

	String uniName = null;
	String universityId = null;

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

			mess.setMessId(messId);
			mess.setMessNo(messNo);
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
			session.save(warden);

			// Save to Manager table
			Manager manager = new Manager();
			manager.setName(managerName);
			manager.setEmail(managerEmail);
			manager.setMessId(messId);

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

	protected boolean addData(ArrayList<String> list) {

		// Retrieve form data
		int messNo = Integer.parseInt(list.get(0));
		String wardenName = list.get(1);
		String wardenEmail = list.get(2);
		String managerName = list.get(3);
		String managerEmail = list.get(4);
		String messLocation = list.get(5);
		int allowableOptOut = Integer.parseInt(list.get(6));

		MessIdGenerator messIdGen = new MessIdGenerator();
//		String messId = messIdGen.messId(uniName, messNo);
		String messId = messNo+"sdfkslaf";

		if (universityId == null) {
			// Logout servlet

		}

		// Save mess to database
		boolean success = saveMess(messNo, messId, universityId, wardenName, wardenEmail, managerName, managerEmail,
				messLocation, allowableOptOut);

		if (success) {
			return true;// Redirect to success page
		} else {
			return false;// Redirect back to add mess page with error parameter
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
