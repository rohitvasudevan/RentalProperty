package com.rentprop.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rentprop.dto.ContactUsDTO;
import com.rentprop.entity.ContactUs;
import com.rentprop.util.HibernateUtil;

public class ContactUsDAO {
	public void addContactUs(ContactUsDTO contactUsDto) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			ContactUs contactUs = new ContactUs();
			BeanUtils.copyProperties(contactUs, contactUsDto);

			session.save(contactUs);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
