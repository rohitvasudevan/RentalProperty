package com.rentprop.dto;

import java.io.Serializable;

public class MaintenanceRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int serviceId;
	private String serviceType;
	private String serviceDescription;
	private ResidentDTO residentDTO;
	/**
	 * @return the serviceId
	 */
	public int getServiceId() {
		return serviceId;
	}
	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the serviceDescription
	 */
	public String getServiceDescription() {
		return serviceDescription;
	}
	/**
	 * @param serviceDescription the serviceDescription to set
	 */
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	/**
	 * @return the residentDTO
	 */
	public ResidentDTO getResidentDTO() {
		return residentDTO;
	}
	/**
	 * @param residentDTO the residentDTO to set
	 */
	public void setResidentDTO(ResidentDTO residentDTO) {
		this.residentDTO = residentDTO;
	}
	

}
