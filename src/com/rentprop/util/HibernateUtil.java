package com.rentprop.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Exception stack Trace ************** begin");
			System.err.println("Hibernate : Initial SessionFactory creation failed." + ex);
			ex.printStackTrace();
			System.err.println("Exception Stack Trace ********* END");
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
	public static void closeSession(Session session) {
		if (null != session && session.isOpen()) {
			session.close();
		}
	}
}
