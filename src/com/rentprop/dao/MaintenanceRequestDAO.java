package com.rentprop.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rentprop.dto.MaintenanceRequestDTO;
import com.rentprop.dto.ReplyDTO;
import com.rentprop.dto.ResidentDTO;
import com.rentprop.entity.MaintenanceRequest;
import com.rentprop.entity.Reply;
import com.rentprop.entity.Resident;
import com.rentprop.util.HibernateUtil;

public class MaintenanceRequestDAO {
	
	public int addService(MaintenanceRequestDTO maintenanceRequestDTO) {
		int serviceId = -1;
		MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
		Resident resident = new Resident();
		Session session = HibernateUtil.getSession();
		try {
			BeanUtils.copyProperties(resident, maintenanceRequestDTO.getResidentDTO());
			BeanUtils.copyProperties(maintenanceRequest, maintenanceRequestDTO);
			maintenanceRequest.setResident(resident);
			Transaction transaction = session.beginTransaction();

			serviceId = (Integer) session.save(maintenanceRequest);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return serviceId;
	}

	public List<MaintenanceRequestDTO> search_all_services() {
		List<MaintenanceRequestDTO> maintenanceRequestDTOList = new ArrayList<MaintenanceRequestDTO>();
		List<MaintenanceRequest> maintenanceRequestList = new ArrayList<MaintenanceRequest>();
		MaintenanceRequestDTO reqDto = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from MaintenanceRequest");
			maintenanceRequestList = q.list();
			for (MaintenanceRequest req : maintenanceRequestList) {
				reqDto = new MaintenanceRequestDTO();
				BeanUtils.copyProperties(reqDto, req);

				if (null != req.getResident()) {
					ResidentDTO residentDTO = new ResidentDTO();
					BeanUtils.copyProperties(residentDTO, req.getResident());
					reqDto.setResidentDTO(residentDTO);
				}
				maintenanceRequestDTOList.add(reqDto);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return maintenanceRequestDTOList;
	}

	public MaintenanceRequestDTO findServiceReqById(int serviceId) {
		MaintenanceRequestDTO maintenanceRequestDTOFound = null;
		Session session = HibernateUtil.getSession();
		try {
			MaintenanceRequest maintenanceRequest = null;
			maintenanceRequest = session.get(MaintenanceRequest.class, new Integer(serviceId));
			if (null != maintenanceRequest) {
				maintenanceRequestDTOFound = new MaintenanceRequestDTO();
				BeanUtils.copyProperties(maintenanceRequestDTOFound, maintenanceRequest);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return maintenanceRequestDTOFound;
	}

	public void deleteMaintRequest(MaintenanceRequestDTO maintenanceRequestDTO) {
		MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
		Session session = HibernateUtil.getSession();
		try {
			BeanUtils.copyProperties(maintenanceRequest, maintenanceRequestDTO);
			System.out.println("key " + maintenanceRequest.getServiceId());
			Transaction transaction = session.beginTransaction();
			session.delete(maintenanceRequest);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void addReply(ReplyDTO replyDTO) {
		Reply reply = new Reply();
		MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
		Resident resident = new Resident();
		Session session = HibernateUtil.getSession();
		try {
			BeanUtils.copyProperties(reply, replyDTO);
			BeanUtils.copyProperties(resident, replyDTO.getResidentDTO());
			BeanUtils.copyProperties(maintenanceRequest, replyDTO.getMaintenanceRequestDTO());
			reply.setResident(resident);
			reply.setMaintenanceRequest(maintenanceRequest);

			Transaction transaction = session.beginTransaction();
			session.save(reply);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
