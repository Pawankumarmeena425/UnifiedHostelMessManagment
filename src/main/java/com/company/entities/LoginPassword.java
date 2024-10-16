package com.company.entities;

//import javax.persistence.*;
import jakarta.persistence.*;


@Entity
@Table(name = "login_password")
public class LoginPassword {

	
	@Column(name = "mobile_no")
	private Long mobileNo;

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "category")
	private String category;



	public LoginPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginPassword(Long mobileNo, String email, String password, String category) {
		super();
		this.mobileNo = mobileNo;
		this.email = email;
		this.password = password;
		this.category = category;
	}

	public String getCategory() {
		return category;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
