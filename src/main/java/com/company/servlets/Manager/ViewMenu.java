package com.company.servlets.Manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.company.entities.ListOfMeal;
import com.company.entities.Meal;
import com.company.entities.Student;
import com.company.helper.JsonConverter;
import com.company.helper.MealIdGenerator;
import com.mysql.cj.xdevapi.JsonArray;

/**
 * Servlet implementation class ViewMenu
 */
public class ViewMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewMenu() {
		super();
		// TODO Auto-generated constructor stub
	}

	private List<ListOfMeal> fetchMenuDetails(String messId, String mealId) {
		try {

			// Create Hibernate session
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Meal meal = session.get(Meal.class, mealId);

			if (meal != null) {
				List<ListOfMeal> listOfMeal = session
						.createQuery("Select item from ListOfMeal where meal.mealId=:mealId", ListOfMeal.class)
						.setParameter("mealId", mealId).getResultList();
				session.close();
				return listOfMeal;

			} else {
				session.close();
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null; // Return null if an error occurs
		}
	}

	private String objectToJson(List<ListOfMeal> listOfMealM, List<ListOfMeal> listOfMealE) {

		List<String> morningMeal = new ArrayList<String>();
		List<String> eveningMeal = new ArrayList<String>();

		for (ListOfMeal lm : listOfMealM) {
			morningMeal.add(lm.getItem());
		}
		for (ListOfMeal lm : listOfMealE) {
			eveningMeal.add(lm.getItem());
		}

		StringBuilder jsonData = new StringBuilder();
		jsonData.append('{' + '\n' + '	' + "foodItemsMorning: [");
		for (int i = 0; i < morningMeal.size(); i++) {
			jsonData.append(morningMeal.get(i) + ',');

		}
		jsonData.deleteCharAt(jsonData.length() - 1);
		jsonData.append(']' + ',' + '\n' + '	' + "foodItemsEvening: [");

		for (int i = 0; i < eveningMeal.size(); i++) {
			jsonData.append(eveningMeal.get(i) + ',');

		}
		jsonData.deleteCharAt(jsonData.length() - 1);
		jsonData.append(']' + '\n' + '}');

		return jsonData.toString();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve stored OTP from session
		HttpSession session = request.getSession();
//		String messId = (String) session.getAttribute("messId");
		
		
		String messId = (String) request.getParameter("messId");

		
		Date currentDate = new Date();

		String mealIdM = new MealIdGenerator().mealId(messId, currentDate, "Morning");
		String mealIdE = new MealIdGenerator().mealId(messId, currentDate, "Evening");

		System.out.println(messId);
		System.out.println(mealIdM);

		List<ListOfMeal> listOfMealM = fetchMenuDetails(messId, mealIdM);
		List<ListOfMeal> listOfMealE = fetchMenuDetails(messId, mealIdE);

		PrintWriter out = response.getWriter();

		JsonConverter jc = new JsonConverter();
//		out.println(listOfMealE);
		
		
		
		
		
		
		
		
		
		
		
		
		
		out.println(jc.MenuObjectToJson(listOfMealM));

		out.println(jc.MenuObjectToJson(listOfMealE));



//		String jsonData = objectToJson(listOfMealM, listOfMealE);
//		out.println(jsonData);

	}

}
