package com.rentprop.dto;

import java.io.Serializable;

import java.util.Date;

public class PaymentDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int payment_id;
	private String date;
	//private float rent;
	private String status;
	private ApartmentDTO apartmentDTO;
	private ResidentDTO residentDTO;
	private Date payment_date;
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	/*public float getRent() {
		return rent;
	}
	public void setRent(float rent) {
		this.rent = rent;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}
	/**
	 * @return the apartmentDTO
	 */
	public ApartmentDTO getApartmentDTO() {
		return apartmentDTO;
	}
	/**
	 * @param apartmentDTO the apartmentDTO to set
	 */
	public void setApartmentDTO(ApartmentDTO apartmentDTO) {
		this.apartmentDTO = apartmentDTO;
	}
	/**
	 * @return the residentDTO
	 */
	public ResidentDTO getResidentDTO() {
		return residentDTO;
	}
	/**
	 * @param residentDTO the residentDTO to set
	 */
	public void setResidentDTO(ResidentDTO residentDTO) {
		this.residentDTO = residentDTO;
	}
	
	
}
