package com.company.entities;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "rating")
public class Rating {
	@Id
	@Column(name = "rating_id")
	private String ratingId;

	@Column(name = "student_id")
	private String studentId;

	@Column(name = "mess_id")
	private String messId;

	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "time")
	private String time;

	@Column(name = "rating")
	private int rating;

	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rating(String ratingId, String studentId, String messId, Date date, String time, int rating) {
		super();
		this.ratingId = ratingId;
		this.studentId = studentId;
		this.messId = messId;
		this.date = date;
		this.time = time;
		this.rating = rating;
	}

	public String getRatingId() {
		return ratingId;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getMessId() {
		return messId;
	}

	public void setMessId(String messId) {
		this.messId = messId;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
