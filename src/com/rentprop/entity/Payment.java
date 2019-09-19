package com.rentprop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PAYMENT_ID")
	private int payment_id;

	private String date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PAYMENT_DATE")
	private Date payment_date;

	private String status;

	//bi-directional many-to-one association to Apartment
	@ManyToOne
	@JoinColumn(name="APARTMENT_ID")
	private Apartment apartment;

	//bi-directional many-to-one association to Resident
	@ManyToOne
	@JoinColumn(name="RESIDENT_ID")
	private Resident resident;

	public Payment() {
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the payment_id
	 */
	public int getPayment_id() {
		return payment_id;
	}

	/**
	 * @param payment_id the payment_id to set
	 */
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	/**
	 * @return the payment_date
	 */
	public Date getPayment_date() {
		return payment_date;
	}
	/**
	 * @param payment_date the payment_date to set
	 */
	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Apartment getApartment() {
		return this.apartment;
	}
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	public Resident getResident() {
		return this.resident;
	}
	public void setResident(Resident resident) {
		this.resident = resident;
	}
}
