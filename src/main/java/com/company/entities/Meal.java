package com.company.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "meal")
public class Meal {

	@Id
	@Column(name = "meal_id")
	private String mealId;

	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "time")
	private String time;

	@Column(name = "messId")
	private String messId;

	@OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
	private List<ListOfMeal> listOfMeals;

	public Meal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Meal(String mealId, Date date, String time, String messId) {
		super();
		this.mealId = mealId;
		this.date = date;
		this.time = time;
		this.messId = messId;
	}

	public String getMealId() {
		return mealId;
	}

	public void setMealId(String mealId) {
		this.mealId = mealId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessId() {
		return messId;
	}

	public void setMessId(String messId) {
		this.messId = messId;
	}

}
