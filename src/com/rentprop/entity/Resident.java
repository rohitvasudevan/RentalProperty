package com.rentprop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the resident database table.
 * 
 */
@Entity
@NamedQuery(name="Resident.findAll", query="SELECT r FROM Resident r")
public class Resident implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RESIDENT_ID")
	private int residentId;

	private String address;

	//bi-directional many-to-one association to MaintenanceRequest
	@OneToMany(mappedBy="resident")
	private List<MaintenanceRequest> maintenanceRequests;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="resident")
	private List<Payment> payments;

	//bi-directional many-to-one association to Reply
	@OneToMany(mappedBy="resident")
	private List<Reply> replies;

	//bi-directional many-to-one association to Apartment
	@ManyToOne
	@JoinColumn(name="APARTMENT_ID")
	private Apartment apartment;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USERNAME")
	private User user;

	public Resident() {
	}

	public int getResidentId() {
		return this.residentId;
	}

	public void setResidentId(int residentId) {
		this.residentId = residentId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<MaintenanceRequest> getMaintenanceRequests() {
		return this.maintenanceRequests;
	}

	public void setMaintenanceRequests(List<MaintenanceRequest> maintenanceRequests) {
		this.maintenanceRequests = maintenanceRequests;
	}

	public MaintenanceRequest addMaintenanceRequest(MaintenanceRequest maintenanceRequest) {
		getMaintenanceRequests().add(maintenanceRequest);
		maintenanceRequest.setResident(this);

		return maintenanceRequest;
	}

	public MaintenanceRequest removeMaintenanceRequest(MaintenanceRequest maintenanceRequest) {
		getMaintenanceRequests().remove(maintenanceRequest);
		maintenanceRequest.setResident(null);

		return maintenanceRequest;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setResident(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setResident(null);

		return payment;
	}

	public List<Reply> getReplies() {
		return this.replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public Reply addReply(Reply reply) {
		getReplies().add(reply);
		reply.setResident(this);

		return reply;
	}

	public Reply removeReply(Reply reply) {
		getReplies().remove(reply);
		reply.setResident(null);

		return reply;
	}

	public Apartment getApartment() {
		return this.apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}