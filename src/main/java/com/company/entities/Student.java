package com.company.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "roll_no")
	private String rollNo;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name = "nfc_id")
	private String nfcId;

	@Column(name = "mobile_no")
	private Long mobileNo;

	@Column(name = "email")
	private String email;

	@Column(name = "branch")
	private String branch;

	@Column(name = "mess_no")
	private String mesh_NO;

	@Column(name = "mess_id")
	private String mesh_Id;

	@Column(name = "university_id")
	private String university_Id;

//	@Lob
//	@Column(columnDefinition = "longblob")
//	private byte[] photo;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String rollNo, String fullName, Date dob, String nfcId, Long mobileNo, String email, String branch,
			String mesh_NO, String mesh_Id, String university_Id) {
		super();
		this.rollNo = rollNo;
		this.fullName = fullName;
		this.dob = dob;
		this.nfcId = nfcId;
		this.mobileNo = mobileNo;
		this.email = email;
		this.branch = branch;
		this.mesh_NO = mesh_NO;
		this.mesh_Id = mesh_Id;
		this.university_Id = university_Id;

	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getNfcId() {
		return nfcId;
	}

	public void setNfcId(String nfcId) {
		this.nfcId = nfcId;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getMesh_NO() {
		return mesh_NO;
	}

	public void setMesh_NO(String mesh_NO) {
		this.mesh_NO = mesh_NO;
	}

	public String getMesh_Id() {
		return mesh_Id;
	}

	public void setMesh_Id(String mesh_Id) {
		this.mesh_Id = mesh_Id;
	}

	public String getUniversity_Id() {
		return university_Id;
	}

	public void setUniversity_Id(String university_Id) {
		this.university_Id = university_Id;
	}

//	public byte[] getPhoto() {
//		return photo;
//	}
//
//
//
//	public void setPhoto(byte[] photo) {
//		this.photo = photo;
//	}

}
