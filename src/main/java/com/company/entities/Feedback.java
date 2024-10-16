package com.company.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "feedback")
public class Feedback {

	@Id
	@Column(name = "feedback_id")
	private String feedbackId;

	@Column(name = "date")
	private Date date;

	@Column(name = "mess_id")
	private String messId;

	@Column(name = "student_id")
	private String studentId;

	@Column(name = "time")
	private String time;

	@Column(name = "text")
	private String text;

	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] photo;

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feedback(String feedbackId, Date date, String messId, String studentId, String time, String text,
			byte[] photo) {
		super();
		this.feedbackId = feedbackId;
		this.date = date;
		this.messId = messId;
		this.studentId = studentId;
		this.time = time;
		this.text = text;
		this.photo = photo;
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessId() {
		return messId;
	}

	public void setMessId(String messId) {
		this.messId = messId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

}
