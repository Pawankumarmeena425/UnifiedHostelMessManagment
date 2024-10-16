package com.company.servlets.Manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.entities.ListOfMeal;
import com.company.entities.Meal;
import com.company.helper.MealIdGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class AddMenu
 */
public class AddMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddMenu() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean insertMenu(String time, Date currentDate, String messId, String mealId, JsonNode mealsNode) {
		try {
			// Create a new meal
			Meal meal = new Meal();
			meal.setMealId(mealId);
			meal.setTime(time);
			meal.setDate(currentDate);
			meal.setMessId(messId);

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			session.save(meal);

			// Create ListOfMeal entities for each meal item
			for (JsonNode mealNode : mealsNode) {
				String itemName = mealNode.asText();
				ListOfMeal listOfMeal = new ListOfMeal(itemName, meal);
				session.save(listOfMeal);
			}

			// Commit the transaction
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Read JSON data from request
		BufferedReader reader = request.getReader();
		StringBuilder jsonStr = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			jsonStr.append(line);
		}
//		System.out.println(jsonStr);
		// Parse JSON data
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(jsonStr.toString());

		// Retrieve time and list of meals from JSON data
		String time = jsonNode.get("mealTime").asText();
		String messId = jsonNode.get("messId").asText();
		JsonNode mealsNode = jsonNode.get("foodItems");
		System.out.println(time);
		System.out.println(mealsNode);

		// Retrieve current date
		Date currentDate = new Date();

		// Retrieve mess ID from session
		HttpSession session = request.getSession();
//		String messId = (String) session.getAttribute("messId");
//		messId = "MBMUJ123";

		String mealId = new MealIdGenerator().mealId(messId, currentDate, time);
		// Insert data into Meal and ListOfMeal tables
		boolean success = insertMenu(time, currentDate, messId, mealId, mealsNode);

		PrintWriter out = response.getWriter();
		if (success) {
			out.println("Successfully Inserted !!");
		} else {
			out.println("Something Went Wrong !! Data already inserted");
		}
	}

}
