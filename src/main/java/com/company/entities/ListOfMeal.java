package com.company.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "list_of_meal")
public class ListOfMeal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "item")
	private String item;

	@ManyToOne
	@JoinColumn(name = "meal_id")
	private Meal meal;

	public ListOfMeal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListOfMeal(String item, Meal meal) {
		super();

		this.item = item;
		this.meal = meal;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

}