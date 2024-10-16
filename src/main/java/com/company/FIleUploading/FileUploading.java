package com.company.FIleUploading;

import java.io.File;
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
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.JPopupMenu.Separator;

import org.apache.commons.fileupload2.core.DiskFileItemFactory;

import com.mysql.cj.result.DefaultValueFactory;

/**
 * Servlet implementation class FileUploading
 */

@MultipartConfig
public class FileUploading extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileUploading() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Part part = request.getPart("file");
		String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		InputStream fileContent = part.getInputStream();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fileUploading", "root",
					"#Pawan@123");

			PreparedStatement st = conn.prepareStatement("insert into files (fileName) values (?)");
			st.setString(1, fileName);

			int i = st.executeUpdate();
			if (i == 1) {
//				String path = getServletContext().getRealPath("") + "files";
//				System.out.println(path);
//				File file = new File(path);
//				part.write(file + File.separator + fileName);

//				String uploadDir = "C:/Users/Pawan ji/Downloads/files";
				String path = getServletContext().getRealPath("") + "files";
				Path uploadPath = Paths.get(path);
				System.out.println(path);

				if (!Files.exists(uploadPath)) {

					Files.createDirectories(uploadPath);
				}
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);

				response.getWriter().println("File" + fileName + "has been Uploaded!!");

				System.out.println("Upload Successfully !!");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
