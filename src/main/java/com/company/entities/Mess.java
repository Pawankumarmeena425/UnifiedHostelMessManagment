package com.company.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mess")
public class Mess {

	@Id
	@Column(name = "mess_id")
	private String messId;

	@Column(name = "messNo")
	private int messNo;

	@Column(name = "university_id")
	private String universityId;

	@Column(name = "warden_id")
	private String wardenId;

	@Column(name = "manager_id")
	private String managerId;

	private String location;

	@Column(name = "allowable_optout")
	private int allowable_optout;

	public Mess() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mess(String messId, int messNo, String universityId, String wardenId, String managerId, String location,
			int allowable_optout) {
		super();
		this.messId = messId;
		this.messNo = messNo;
		this.universityId = universityId;
		this.wardenId = wardenId;
		this.managerId = managerId;
		this.location = location;
		this.allowable_optout = allowable_optout;
	}

	public String getMessId() {
		return messId;
	}

	public void setMessId(String messId) {
		this.messId = messId;
	}

	public int getMessNo() {
		return messNo;
	}

	public void setMessNo(int messNo) {
		this.messNo = messNo;
	}

	public String getUniversityId() {
		return universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public String getWardenId() {
		return wardenId;
	}

	public void setWardenId(String wardenId) {
		this.wardenId = wardenId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAllowable_optout() {
		return allowable_optout;
	}

	public void setAllowable_optout(int allowable_optout) {
		this.allowable_optout = allowable_optout;
	}

}
