package com.rentprop.dto;

import java.io.Serializable;

public class ReplyDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int replyId;
	private MaintenanceRequestDTO maintenanceRequestDTO;
	private ResidentDTO residentDTO;
	private String replyMessage;
	/**
	 * @return the reply_id
	 */
	public int getReplyId() {
		return replyId;
	}
	/**
	 * @param reply_id the reply_id to set
	 */
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	/**
	 * @return the maintenanceRequestDTO
	 */
	public MaintenanceRequestDTO getMaintenanceRequestDTO() {
		return maintenanceRequestDTO;
	}
	/**
	 * @param maintenanceRequestDTO the maintenanceRequestDTO to set
	 */
	public void setMaintenanceRequestDTO(MaintenanceRequestDTO maintenanceRequestDTO) {
		this.maintenanceRequestDTO = maintenanceRequestDTO;
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
	/**
	 * @return the reply_message
	 */
	public String getReplyMessage() {
		return replyMessage;
	}
	/**
	 * @param reply_message the reply_message to set
	 */
	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}
		
}
