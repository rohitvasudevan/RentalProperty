package com.rentprop.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.AppointmentDTO;
import com.rentprop.dto.RentalRequestDTO;
import com.rentprop.entity.Apartment;
import com.rentprop.entity.Appointment;
import com.rentprop.entity.RentalRequest;
import com.rentprop.util.HibernateUtil;

public class AppointmentDAO {

	public static int insertNewAppointment(AppointmentDTO appointmentDTO) {
		int appointmentId = -1;
		Appointment appointment = new Appointment();
		Apartment apartment = new Apartment();
		Session session = HibernateUtil.getSession();
		try {
			BeanUtils.copyProperties(appointment, appointmentDTO);
			BeanUtils.copyProperties(apartment, appointmentDTO.getApartmentDTO());
			appointment.setApartment(apartment);
			Transaction transaction = session.beginTransaction();
			appointmentId = (Integer) session.save(appointment);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return appointmentId;
	}

	public static AppointmentDTO findAppointmentById(int appointmentId) {
		AppointmentDTO appointmentDTOFound = null;
		Session session = HibernateUtil.getSession();
		try {
			Appointment appointment = session.get(Appointment.class, new Integer(appointmentId));
			if (null != appointment) {
				appointmentDTOFound = new AppointmentDTO();
				BeanUtils.copyProperties(appointmentDTOFound, appointment);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return appointmentDTOFound;
	}

	public List<RentalRequestDTO> searchRentalRequest(RentalRequestDTO RentalRequestDTO) {
		List<RentalRequestDTO> rentalReqDtoList = new ArrayList<RentalRequestDTO>();
		List<RentalRequest> rentalRequestList = new ArrayList<RentalRequest>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from RentalRequest where rentalRequestId='"
					+ RentalRequestDTO.getRentalRequestId() + "' and emailId='" + RentalRequestDTO.getEmailId() + "'");
			rentalRequestList = q.list();
			for (RentalRequest rentalRequest : rentalRequestList) {
				RentalRequestDTO rentalRequestDTOTemp = new RentalRequestDTO();
				BeanUtils.copyProperties(rentalRequestDTOTemp, rentalRequest);
				rentalReqDtoList.add(rentalRequestDTOTemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return rentalReqDtoList;
	}

	public List<RentalRequestDTO> getRentalData(RentalRequestDTO rentalRequestDTO) {
		List<RentalRequestDTO> rentalReqDtoList = new ArrayList<RentalRequestDTO>();
		List<RentalRequest> rentalRequestList = new ArrayList<RentalRequest>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery(
					"from RentalRequest where rentalRequestId='" + rentalRequestDTO.getRentalRequestId() + "'");
			rentalRequestList = q.list();
			for (RentalRequest rentalRequest : rentalRequestList) {
				RentalRequestDTO rentalRequestDTOTemp = new RentalRequestDTO();
				BeanUtils.copyProperties(rentalRequestDTOTemp, rentalRequest);
				rentalReqDtoList.add(rentalRequestDTOTemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return rentalReqDtoList;
	}

	public AppointmentDTO fetch_appointment(AppointmentDTO appointmentDTO) {
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		AppointmentDTO appointmentDTOFound = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from Appointment where appointment_id='" + appointmentDTO.getAppointmentId()
					+ "' and emailid='" + appointmentDTO.getEmailid() + "'");
			appointmentList = q.list();
			if (appointmentList != null && appointmentList.size() == 1) {
				Appointment appointment = appointmentList.get(0);
				appointmentDTOFound = new AppointmentDTO();
				BeanUtils.copyProperties(appointmentDTOFound, appointment);

				ApartmentDTO apartmentDTO = new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDTO, appointment.getApartment());
				appointmentDTOFound.setApartmentDTO(apartmentDTO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return appointmentDTOFound;
	}

	public List<RentalRequestDTO> findRentalRequest(RentalRequestDTO rentalRequestDTO) {
		List<RentalRequestDTO> rentalRequestDTOList = new ArrayList<RentalRequestDTO>();
		List<RentalRequest> rentalRequestList = new ArrayList<RentalRequest>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from RentalRequest where rentalRequestId='"
					+ rentalRequestDTO.getRentalRequestId() + "' and emailId='" + rentalRequestDTO.getEmailId() + "'");
			rentalRequestList = q.list();

			for (RentalRequest rentalReq : rentalRequestList) {

				RentalRequestDTO rentalRequestDtoTemp = new RentalRequestDTO();
				BeanUtils.copyProperties(rentalRequestDtoTemp, rentalReq);

				ApartmentDTO apartmentDTO = new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDTO, rentalReq.getApartment());
				rentalRequestDtoTemp.setApartmentDTO(apartmentDTO);
				rentalRequestDTOList.add(rentalRequestDtoTemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return rentalRequestDTOList;
	}

	public void update_appointment(AppointmentDTO AppointmentDTO) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();

			Query q = session.createQuery("update Appointment set firstname = '" + AppointmentDTO.getFirstname()
					+ "', lastname='" + AppointmentDTO.getLastname() + "', emailid='" + AppointmentDTO.getEmailid()
					+ "', phoneno='" + AppointmentDTO.getPhoneno() + "', address='" + AppointmentDTO.getAddress()
					+ "', date='" + AppointmentDTO.getDate() + "', slot='" + AppointmentDTO.getSlot()
					+ "' where appointment_id='" + AppointmentDTO.getAppointmentId() + "'");
			int result = q.executeUpdate();

			session.flush();
			session.clear();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}

	}

	public void delete_appointment(AppointmentDTO appointmentDTO) {
		Appointment appointment = new Appointment();
		Session session = HibernateUtil.getSession();
		try {
			BeanUtils.copyProperties(appointment, appointmentDTO);
			Transaction transaction = session.beginTransaction();
			session.delete(appointment);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void delete_rental(RentalRequestDTO rentalRequestDTO) {
		RentalRequest rentalRequest = new RentalRequest();
		Session session = HibernateUtil.getSession();
		try {
			BeanUtils.copyProperties(rentalRequest, rentalRequestDTO);
			Transaction transaction = session.beginTransaction();
			session.delete(rentalRequest);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
