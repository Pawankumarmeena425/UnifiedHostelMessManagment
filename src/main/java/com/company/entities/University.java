package com.company.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "university")
public class University {

	@Column(name = "name")
	private String name;

	@Column(name = "location")
	private String location;

	@Column(name = "logo")
	private String logo;

	@Id
	@Column(name = "university_email")
	private String universityEmail;

	
	@Column(name = "university_mobile")
	private Long universityMobile;

	public University() {
		super();
		// TODO Auto-generated constructor stub
	}



	public University(String name, String location, String logo, String universityEmail, Long universityMobile) {
		super();
		this.name = name;
		this.location = location;
		this.logo = logo;
		this.universityEmail = universityEmail;
		this.universityMobile = universityMobile;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUniversityEmail() {
		return universityEmail;
	}

	public void setUniversityEmail(String universityEmail) {
		this.universityEmail = universityEmail;
	}

	public Long getUniversityMobile() {
		return universityMobile;
	}

	public void setUniversityMobile(Long universityMobile) {
		this.universityMobile = universityMobile;
	}

}
