package com.rentprop.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contact_us database table.
 * 
 */
@Entity
@Table(name="contact_us")
@NamedQuery(name="ContactUs.findAll", query="SELECT c FROM ContactUs c")
public class ContactUs implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CONTACT_US_ID")
	private int contactUsId;

	@Column(name="EMAIL_ID")
	private String emailId;

	private String message;

	private String name;

	private String phone;

	public ContactUs() {
	}

	public int getContactUsId() {
		return this.contactUsId;
	}

	public void setContactUsId(int contactUsId) {
		this.contactUsId = contactUsId;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}