package com.company.servlets;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.company.entities.LoginPassword;
import com.company.entities.University;

public class check {
	public static void main(String[] args) {

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();

		Transaction tx = session.beginTransaction();

		String uniName = "MBM university";
		String uniId = "2313";
		String location = "Jodhpur";
		String email = "mbm@gmail.com";
		String m = "9523453543";
		long mobile = Long.parseLong(m);
		String pass = "Mbm@123";

		University uni = new University();
		uni.setName(uniName);
		
		uni.setLocation(location);
		uni.setUniversityEmail(email);
		uni.setUniversityMobile(mobile);

		LoginPassword loginpass = new LoginPassword();
		loginpass.setEmail(email);
		loginpass.setMobileNo(mobile);
		loginpass.setPassword(pass);
		loginpass.setCategory("University");

		session.save(uni);
		session.save(loginpass);

		tx.commit();

		session.close();

	}

}
