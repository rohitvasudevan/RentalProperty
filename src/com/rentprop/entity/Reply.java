package com.rentprop.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the reply database table.
 * 
 */
@Entity
@NamedQuery(name="Reply.findAll", query="SELECT r FROM Reply r")
public class Reply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="REPLY_ID")
	private int replyId;

	@Column(name="REPLY_MESSAGE")
	private String replyMessage;

	//bi-directional many-to-one association to MaintenanceRequest
	@ManyToOne
	@JoinColumn(name="SERVICE_ID")
	private MaintenanceRequest maintenanceRequest;

	//bi-directional many-to-one association to Resident
	@ManyToOne
	@JoinColumn(name="RESIDENT_ID")
	private Resident resident;

	public Reply() {
	}

	public int getReplyId() {
		return this.replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public String getReplyMessage() {
		return this.replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public MaintenanceRequest getMaintenanceRequest() {
		return this.maintenanceRequest;
	}

	public void setMaintenanceRequest(MaintenanceRequest maintenanceRequest) {
		this.maintenanceRequest = maintenanceRequest;
	}

	public Resident getResident() {
		return this.resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

}