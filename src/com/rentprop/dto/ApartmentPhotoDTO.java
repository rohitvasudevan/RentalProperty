package com.rentprop.dto;

import java.io.Serializable;

public class ApartmentPhotoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int photoId;
	private String fileName;
	private String encryptedName;
	private ApartmentDTO apartmentDTO;
	private byte[] photo;

	/**
	 * @return the photoId
	 */
	public int getPhotoId() {
		return photoId;
	}
	/**
	 * @param photoId the photoId to set
	 */
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the encryptedName
	 */
	public String getEncryptedName() {
		return encryptedName;
	}
	/**
	 * @param encryptedName the encryptedName to set
	 */
	public void setEncryptedName(String encryptedName) {
		this.encryptedName = encryptedName;
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
	 * @return the photo
	 */
	public byte[] getPhoto() {
		return photo;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
}
