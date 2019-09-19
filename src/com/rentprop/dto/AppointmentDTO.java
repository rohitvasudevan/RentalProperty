package com.rentprop.dto;

import java.io.Serializable;

public class AppointmentDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int appointmentId;
	private String firstname;
	private String lastname;
	private String emailid;
	private String phoneno;
	private String address;
	private String date;
	private String slot;
	private ApartmentDTO apartmentDTO;
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public ApartmentDTO getApartmentDTO() {
		return apartmentDTO;
	}
	public void setApartmentDTO(ApartmentDTO apartmentDTO) {
		this.apartmentDTO = apartmentDTO;
	}	
}
