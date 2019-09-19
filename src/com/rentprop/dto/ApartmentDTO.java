package com.rentprop.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object
 * 
 */
public class ApartmentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int apartmentId;
	private List<ApartmentPhotoDTO> apartmentPhotos = new ArrayList<ApartmentPhotoDTO>();
	private String address;
	private int noOfBedroom;
	private int noOfBathroom;
	private String aminities;
	private String city;
	private String state;
	private Long zipCode;
	private Float price;
	private String rentalStatus;
//	private String photo;
	/**
	 * @return the apartmentId
	 */
	public int getApartmentId() {
		return apartmentId;
	}
	/**
	 * @param apartmentId the apartmentId to set
	 */
	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}
	/**
	 * @return the fileName
	 */
//	public String getFileName() {
//		return fileName;
//	}
//	/**
//	 * @param fileName the fileName to set
//	 */
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//	/**
//	 * @return the encryptedName
//	 */
//	public String getEncryptedName() {
//		return encryptedName;
//	}
	/**
	 * @param encryptedName the encryptedName to set
	 */
//	public void setEncryptedName(String encryptedName) {
//		this.encryptedName = encryptedName;
//	}
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
	 * @return the bedroom
	 */
	public int getNoOfBedroom() {
		return noOfBedroom;
	}
	/**
	 * @param noOfBedroom the bedroom to set
	 */
	public void setNoOfBedroom(int noOfBedroom) {
		this.noOfBedroom = noOfBedroom;
	}
	/**
	 * @return the bathroom
	 */
	public int getNoOfBathroom() {
		return noOfBathroom;
	}
	/**
	 * @param noOfBathroom the bathroom to set
	 */
	public void setNoOfBathroom(int noOfBathroom) {
		this.noOfBathroom = noOfBathroom;
	}
	/**
	 * @return the aminities
	 */
	public String getAminities() {
		return aminities;
	}
	/**
	 * @param aminities the aminities to set
	 */
	public void setAminities(String aminities) {
		this.aminities = aminities;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zipCode
	 */
	public Long getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}
	/**
	 * @return the rentalStatus
	 */
	public String getRentalStatus() {
		return rentalStatus;
	}
	/**
	 * @param rentalStatus the rentalStatus to set
	 */
	public void setRentalStatus(String rentalStatus) {
		this.rentalStatus = rentalStatus;
	}
	/**
	 * @return the apartmentPhotos
	 */
	public List<ApartmentPhotoDTO> getApartmentPhotos() {
		return apartmentPhotos;
	}
	/**
	 * @param apartmentPhotos the apartmentPhotos to set
	 */
	public void setApartmentPhotos(List<ApartmentPhotoDTO> apartmentPhotos) {
		this.apartmentPhotos = apartmentPhotos;
	}
	
	/**
	 * @return the photo
	 */
//	public String getPhoto() {
//		return photo;
//	}
//	/**
//	 * @param photo the photo to set
//	 */
//	public void setPhoto(String photo) {
//		this.photo = photo;
//	}
	
}
