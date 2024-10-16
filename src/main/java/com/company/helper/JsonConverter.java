package com.company.helper;

import java.util.List;

import com.company.entities.ListOfMeal;
import com.company.entities.Mess;
import com.company.entities.Student;
import com.company.entities.Warden;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
	ObjectMapper mapper = new ObjectMapper();

	public String StudentObjectToJson(Student st) {

		try {
			return mapper.writeValueAsString(st);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String MessObjectToJson(Mess mess) {

		try {
			return mapper.writeValueAsString(mess);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String MenuObjectToJson(List<ListOfMeal> listOfMeal) {

		try {
			return mapper.writeValueAsString(listOfMeal);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String WardenObjectToJson(List<Warden> listOfMeal) {

		try {
			return mapper.writeValueAsString(listOfMeal);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String StudentObjectToJson(List<Student> student) {

		try {
			return mapper.writeValueAsString(student);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
