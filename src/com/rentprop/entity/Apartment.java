package com.rentprop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the apartment database table.
 * 
 */
@Entity
@NamedQuery(name="Apartment.findAll", query="SELECT a FROM Apartment a")
public class Apartment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="APARTMENT_ID")
	private int apartmentId;

	private String address;

	private String aminities;

	private String city;

	@Column(name="ENCRYPTED_NAME")
	private String encryptedName;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="NO_OF_BATHROOM")
	private int noOfBathroom;

	@Column(name="NO_OF_BEDROOM")
	private int noOfBedroom;

	private Float price;

	@Column(name="RENTAL_STATUS")
	private String rentalStatus;

	private String state;

	@Column(name="ZIP_CODE")
	private Integer zipCode;

	//bi-directional many-to-one association to ApartmentPhoto
	@OneToMany(mappedBy="apartment")
	private List<ApartmentPhoto> apartmentPhotos;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="apartment")
	private List<Appointment> appointments;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="apartment")
	private List<Payment> payments;

	//bi-directional many-to-one association to RentalRequest
	@OneToMany(mappedBy="apartment")
	private List<RentalRequest> rentalRequests;

	//bi-directional many-to-one association to Resident
	@OneToMany(mappedBy="apartment")
	private List<Resident> residents;

	public Apartment() {
	}

	public int getApartmentId() {
		return this.apartmentId;
	}

	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAminities() {
		return this.aminities;
	}

	public void setAminities(String aminities) {
		this.aminities = aminities;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public int getNoOfBathroom() {
		return this.noOfBathroom;
	}

	public void setNoOfBathroom(int noOfBathroom) {
		this.noOfBathroom = noOfBathroom;
	}

	public int getNoOfBedroom() {
		return this.noOfBedroom;
	}

	public void setNoOfBedroom(int noOfBedroom) {
		this.noOfBedroom = noOfBedroom;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getRentalStatus() {
		return this.rentalStatus;
	}

	public void setRentalStatus(String rentalStatus) {
		this.rentalStatus = rentalStatus;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public List<ApartmentPhoto> getApartmentPhotos() {
		return this.apartmentPhotos;
	}

	public void setApartmentPhotos(List<ApartmentPhoto> apartmentPhotos) {
		this.apartmentPhotos = apartmentPhotos;
	}

	public ApartmentPhoto addApartmentPhoto(ApartmentPhoto apartmentPhoto) {
		getApartmentPhotos().add(apartmentPhoto);
		apartmentPhoto.setApartment(this);

		return apartmentPhoto;
	}

	public ApartmentPhoto removeApartmentPhoto(ApartmentPhoto apartmentPhoto) {
		getApartmentPhotos().remove(apartmentPhoto);
		apartmentPhoto.setApartment(null);

		return apartmentPhoto;
	}

	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setApartment(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setApartment(null);

		return appointment;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setApartment(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setApartment(null);

		return payment;
	}

	public List<RentalRequest> getRentalRequests() {
		return this.rentalRequests;
	}

	public void setRentalRequests(List<RentalRequest> rentalRequests) {
		this.rentalRequests = rentalRequests;
	}

	public RentalRequest addRentalRequest(RentalRequest rentalRequest) {
		getRentalRequests().add(rentalRequest);
		rentalRequest.setApartment(this);

		return rentalRequest;
	}

	public RentalRequest removeRentalRequest(RentalRequest rentalRequest) {
		getRentalRequests().remove(rentalRequest);
		rentalRequest.setApartment(null);

		return rentalRequest;
	}

	public List<Resident> getResidents() {
		return this.residents;
	}

	public void setResidents(List<Resident> residents) {
		this.residents = residents;
	}

	public Resident addResident(Resident resident) {
		getResidents().add(resident);
		resident.setApartment(this);

		return resident;
	}

	public Resident removeResident(Resident resident) {
		getResidents().remove(resident);
		resident.setApartment(null);

		return resident;
	}

}