package com.revature.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sf = new Configuration().configure().buildSessionFactory();

	// Only ever have one session at a time
	public static Session getSession() {
		return sf.openSession();
	}
	
}
