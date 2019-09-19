package com.rentprop.dto;

import java.io.Serializable; 

public class ResidentDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int residentId;
	private String address;
	private LoginDTO loginDto;
	private ApartmentDTO apartmentDTO;
	
	/**
	 * 
	 */
	public ResidentDTO() {
		super();
	}

	/**
	 * @return the resident_id
	 */
	public int getResidentId() {
		return residentId;
	}

	/**
	 * @param resident_id the resident_id to set
	 */
	public void setResidentId(int resident_id) {
		this.residentId = resident_id;
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
	 * @return the loginDto
	 */
	public LoginDTO getLoginDto() {
		return loginDto;
	}

	/**
	 * @param loginDto the loginDto to set
	 */
	public void setLoginDto(LoginDTO loginDto) {
		this.loginDto = loginDto;
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
