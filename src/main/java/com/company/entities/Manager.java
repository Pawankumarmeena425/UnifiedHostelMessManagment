package com.company.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {

	@Column(name = "name")
	private String name;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Id
	@Column(name = "email")
	private String email;

	// Foregin key
	@Column(name = "mess_id")
	private String messId;

	@Column(name = "university_id")
	private String universityId;

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager(String name, String mobileNo, String email, String messId, String universityId) {
		super();
		this.name = name;
		this.mobileNo = mobileNo;
		this.email = email;
		this.messId = messId;
		this.universityId = universityId;
	}

	public String getUniversityId() {
		return universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessId() {
		return messId;
	}

	public void setMessId(String messId) {
		this.messId = messId;
	}

}
