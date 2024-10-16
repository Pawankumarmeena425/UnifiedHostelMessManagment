package com.company.entities;

import java.util.Date;

import jakarta.persistence.*;;

@Entity
@Table(name = "opt_out")
public class OptOut {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "student_id")
	private String studentId;

	@Column(name = "nfc_id")
	private String nfcId;

	@Column(name = "student_Name")
	private String studentName;

	@Column(name = "mess_id")
	private String messId;

	@Column(name = "date")
	private Date date;

	@Column(name = "time")
	private String time;

	@Column(name = "reason")
	private String reason;

	public OptOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OptOut(String id, String studentId, String nfcId, String studentName, String messId, Date date, String time,
			String reason) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.nfcId = nfcId;
		this.studentName = studentName;
		this.messId = messId;
		this.date = date;
		this.time = time;
		this.reason = reason;
	}

	public String getNfcId() {
		return nfcId;
	}

	public void setNfcId(String nfcId) {
		this.nfcId = nfcId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessId() {
		return messId;
	}

	public void setMessId(String messId) {
		this.messId = messId;
	}

}
