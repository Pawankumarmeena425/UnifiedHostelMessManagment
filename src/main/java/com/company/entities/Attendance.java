package com.company.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "attendance")
public class Attendance {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attendance_id")
	private String attendanceId;

	@Column(name = "student_id")
	private String studentId;

	@Column(name = "nfc_id")
	private String nfcId;

	@Column(name = "student_name")
	private String studnetName;

	@Column(name = "mess_id")
	private String messId;

	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "time")
	private String time;

	@Column(name = "status")
	private String status;

	// Getters and setters

	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Attendance(String attendanceId, String studentId, String nfcId, String studnetName, String messId, Date date,
			String time, String status) {
		super();
		this.attendanceId = attendanceId;
		this.studentId = studentId;
		this.nfcId = nfcId;
		this.studnetName = studnetName;
		this.messId = messId;
		this.date = date;
		this.time = time;
		this.status = status;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}

	public String getStudenId() {
		return studentId;
	}

	public void setStudenId(String studentId) {
		this.studentId = studentId;
	}

	public String getNfcId() {
		return nfcId;
	}

	public void setNfcId(String nfcId) {
		this.nfcId = nfcId;
	}

	public String getStudnetName() {
		return studnetName;
	}

	public void setStudnetName(String studnetName) {
		this.studnetName = studnetName;
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

}
