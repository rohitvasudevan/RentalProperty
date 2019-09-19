package com.rentprop.dto;

import java.io.Serializable;

public class RentalRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rentalRequestId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String address;
	private Long phoneNumber;
	private String status;
	private ApartmentDTO apartmentDTO;
	/**
	 * @return the rental_request_id
	 */
	public int getRentalRequestId() {
		return rentalRequestId;
	}
	/**
	 * @param rental_request_id the rental_request_id to set
	 */
	public void setRentalRequestId(int rentalRequestId) {
		this.rentalRequestId = rentalRequestId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the phoneNumber
	 */
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

}
