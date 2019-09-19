package com.rentprop.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the maintenance_request database table.
 * 
 */
@Entity
@Table(name="maintenance_request")
@NamedQuery(name="MaintenanceRequest.findAll", query="SELECT m FROM MaintenanceRequest m")
public class MaintenanceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SERVICE_ID")
	private int serviceId;

	@Column(name="SERVICE_DESCRIPTION")
	private String serviceDescription;

	@Column(name="SERVICE_TYPE")
	private String serviceType;

	//bi-directional many-to-one association to Resident
	@ManyToOne
	@JoinColumn(name="RESIDENT_ID")
	private Resident resident;

	//bi-directional many-to-one association to Reply
	@OneToMany(mappedBy="maintenanceRequest")
	private List<Reply> replies;

	public MaintenanceRequest() {
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceDescription() {
		return this.serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Resident getResident() {
		return this.resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public List<Reply> getReplies() {
		return this.replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public Reply addReply(Reply reply) {
		getReplies().add(reply);
		reply.setMaintenanceRequest(this);

		return reply;
	}

	public Reply removeReply(Reply reply) {
		getReplies().remove(reply);
		reply.setMaintenanceRequest(null);

		return reply;
	}

}