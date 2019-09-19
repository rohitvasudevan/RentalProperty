package com.rentprop.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the apartment_photo database table.
 * 
 */
@Entity
@Table(name="apartment_photo")
@NamedQuery(name="ApartmentPhoto.findAll", query="SELECT a FROM ApartmentPhoto a")
public class ApartmentPhoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PHOTO_ID")
	private int photoId;

	@Column(name="ENCRYPTED_NAME")
	private String encryptedName;

	@Column(name="FILE_NAME")
	private String fileName;

	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(name="PHOTO")
	private byte[] photo;

	//bi-directional many-to-one association to Apartment
	@ManyToOne
	@JoinColumn(name="APARTMENT_ID")
	private Apartment apartment;

	public ApartmentPhoto() {
	}

	public int getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getEncryptedName() {
		return this.encryptedName;
	}

	public void setEncryptedName(String encryptedName) {
		this.encryptedName = encryptedName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Apartment getApartment() {
		return this.apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

}