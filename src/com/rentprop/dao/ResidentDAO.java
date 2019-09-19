package com.rentprop.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.LoginDTO;
import com.rentprop.dto.PaymentDTO;
import com.rentprop.dto.ResidentDTO;
import com.rentprop.entity.Apartment;
import com.rentprop.entity.Payment;
import com.rentprop.entity.Resident;
import com.rentprop.entity.User;
import com.rentprop.util.HibernateUtil;

public class ResidentDAO {
	
	public int addResident(ResidentDTO residentDTO) {
		int resident_id = -1;
		Session session = HibernateUtil.getSession();
		try {
			User user = new User();
			Resident resident = new Resident();
			Apartment apartment = new Apartment();
			BeanUtils.copyProperties(user, residentDTO.getLoginDto());
			BeanUtils.copyProperties(apartment, residentDTO.getApartmentDTO());
			BeanUtils.copyProperties(resident, residentDTO);
			resident.setUser(user);
			resident.setApartment(apartment);

			Transaction transaction = session.beginTransaction();
			session.save(resident);
			resident_id = resident.getResidentId();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return resident_id;
	}

	public void update_rental_status(ResidentDTO residentDTO) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();

			Query q = session.createQuery("update Apartment set rental_status='rented' where apartment_id='"
					+ residentDTO.getApartmentDTO().getApartmentId() + "'");
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

	public ApartmentDTO findAptById(ApartmentDTO apartmentDTO) {
		ApartmentDTO apartmentDtoFoundInDb = new ApartmentDTO();
		List<ApartmentDTO> list_of_user = new ArrayList<ApartmentDTO>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from Apartment where apartment_id='" + apartmentDTO.getApartmentId() + "'");
			list_of_user = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}

		if (null != list_of_user && list_of_user.size() > 0) {
			try {
				BeanUtils.copyProperties(apartmentDtoFoundInDb, list_of_user.get(0));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return apartmentDtoFoundInDb;
	}

	public List authentication_email(ResidentDTO residentDTO) {
		List<ResidentDTO> list_of_user = new ArrayList<ResidentDTO>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from User where email_id='" + residentDTO.getLoginDto().getEmailId() + "'");
			list_of_user = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return list_of_user;
	}

	public static ResidentDTO findResidentIdByUserName(String userName) {
		ResidentDTO residentDtoFound = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from Resident where username='" + userName + "'");
			List<Resident> residentList = q.list();
			if (residentList != null && residentList.size() == 1) {
				Resident resident = residentList.get(0);
				residentDtoFound = new ResidentDTO();
				BeanUtils.copyProperties(residentDtoFound, resident);
				
				ApartmentDTO apartmentDTO = new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDTO, resident.getApartment());
				residentDtoFound.setApartmentDTO(apartmentDTO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return residentDtoFound;
	}
	
	public static ResidentDTO findResidentIdByResidentId(int residentId) {
		ResidentDTO residentDtoFound = null;
		Session session = HibernateUtil.getSession();
		try {
			Resident resident = null;
			resident = session.get(Resident.class, new Integer(residentId));
			if (null != resident) {
				residentDtoFound = new ResidentDTO();
				BeanUtils.copyProperties(residentDtoFound, resident);
				LoginDTO loginDTO = new LoginDTO();
				BeanUtils.copyProperties(loginDTO, resident.getUser());
				residentDtoFound.setLoginDto(loginDTO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return residentDtoFound;
	}
	
	public int addPayment(PaymentDTO paymentDto) {
		int paymentId = -1;
		Session session = HibernateUtil.getSession();
		try {
			Payment payment = new Payment();

			payment.setDate(paymentDto.getDate());
			payment.setStatus(paymentDto.getStatus());

			Apartment apartment = new Apartment();
			apartment.setApartmentId(paymentDto.getApartmentDTO().getApartmentId());
			payment.setApartment(apartment);

			Resident resident = new Resident();
			resident.setResidentId(paymentDto.getResidentDTO().getResidentId());
			payment.setResident(resident);

			payment.setPayment_date(paymentDto.getPayment_date());

			Transaction transaction = session.beginTransaction();
			paymentId = (Integer) session.save(payment);
			paymentId = payment.getPayment_id();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return paymentId;
	}

	public static List<ResidentDTO> findAllResidentTypeUser() {
		List<ResidentDTO> residentDtoList = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from User where usertype = 'resident'");
			List<User> userList = q.list();
			if (null != userList && userList.size() > 0) {
				residentDtoList = new ArrayList<ResidentDTO>();
				for (User user : userList) {
					LoginDTO loginDto = new LoginDTO();
					BeanUtils.copyProperties(loginDto, user);

					Query qr = session.createQuery("from Resident where username = '" + user.getUsername() + "'");
					List<Resident> residentList = qr.list();
					if (null != residentList && residentList.size() > 0) {
						ResidentDTO residentDto = new ResidentDTO();
						BeanUtils.copyProperties(residentDto, residentList.get(0));
						residentDto.setLoginDto(loginDto);
						residentDtoList.add(residentDto);
					}
				}
			}
			session.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return residentDtoList;
	}

	public List<ResidentDTO> findResidentByUsername(String username) {
		List<Resident> list_of_residents = new ArrayList<Resident>();
		List<ResidentDTO> residentDtoList = new ArrayList<ResidentDTO>();
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();

			Query q = session.createQuery("from Resident where username='" + username + "'");

			list_of_residents = q.list();
			transaction.commit();
			for (Resident resident : list_of_residents) {
				LoginDTO loginDTO = new LoginDTO();
				BeanUtils.copyProperties(loginDTO, resident.getUser());
				ResidentDTO residentDTO = new ResidentDTO();
				BeanUtils.copyProperties(residentDTO, resident);
				residentDTO.setLoginDto(loginDTO);
				residentDtoList.add(residentDTO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return residentDtoList;
	}

	public static List<PaymentDTO> findAllPayments() {
		List<PaymentDTO> paymentDTOList = new ArrayList<PaymentDTO>();
		List<Payment> paymentList = new ArrayList<Payment>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from Payment");

			paymentList = q.list();
			for (Payment payment : paymentList) {
				PaymentDTO paymentDTO = new PaymentDTO();
				BeanUtils.copyProperties(paymentDTO, payment);

				ResidentDTO residentDTO = new ResidentDTO();

				if (null != payment.getResident()) {
					BeanUtils.copyProperties(residentDTO, payment.getResident());

					LoginDTO loginDTO = new LoginDTO();
					BeanUtils.copyProperties(loginDTO, payment.getResident().getUser());
					residentDTO.setLoginDto(loginDTO);
					paymentDTO.setResidentDTO(residentDTO);
				}
				ApartmentDTO apartmentDTO = new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDTO, payment.getApartment());
				paymentDTO.setApartmentDTO(apartmentDTO);

				paymentDTOList.add(paymentDTO);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return paymentDTOList;

	}
	
	public static List<PaymentDTO> findPaymentHistoryByResidentId(int residentId)  {
		List<PaymentDTO> paymentDTOList=new ArrayList<PaymentDTO>();
		List<Payment> paymentList=new ArrayList<Payment>();
		Session session = HibernateUtil.getSession();
		try{
			Query q=session.createQuery("from Payment where status='paid' and resident_Id = '"+residentId+"' order by payment_id desc");
		
			paymentList = q.list();
			for (Payment payment : paymentList) {
				PaymentDTO paymentDTO = new PaymentDTO();
				BeanUtils.copyProperties(paymentDTO, payment);
				 
				ResidentDTO residentDTO = new ResidentDTO();
				
				if (null != payment.getResident()) {
					BeanUtils.copyProperties(residentDTO, payment.getResident());
					
					LoginDTO loginDTO = new LoginDTO();
					BeanUtils.copyProperties(loginDTO, payment.getResident().getUser());
					residentDTO.setLoginDto(loginDTO);
				}
				ApartmentDTO apartmentDTO= new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDTO, payment.getApartment());
				paymentDTO.setApartmentDTO(apartmentDTO);
				paymentDTO.setResidentDTO(residentDTO);
				paymentDTOList.add(paymentDTO);
			}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				HibernateUtil.closeSession(session);
			}
			return paymentDTOList;	
		
	}
	
	public static List<PaymentDTO> findPendingBillByResidentId(int residentId) {
		List<PaymentDTO> paymentDTOList=new ArrayList<PaymentDTO>();
		List<Payment> paymentList=new ArrayList<Payment>();
		Session session = HibernateUtil.getSession();
		try{
			Query q=session.createQuery("from Payment where status in ('pending','failed') and resident_Id = '"+residentId+"'");
			paymentList = q.list();
			for (Payment payment : paymentList) {
				PaymentDTO paymentDTO = new PaymentDTO();
				BeanUtils.copyProperties(paymentDTO, payment);
				 
				ResidentDTO residentDTO = new ResidentDTO();
				if (null != payment.getResident()) {
					BeanUtils.copyProperties(residentDTO, payment.getResident());
					
					LoginDTO loginDTO = new LoginDTO();
					BeanUtils.copyProperties(loginDTO, payment.getResident().getUser());
					residentDTO.setLoginDto(loginDTO);
				}
				ApartmentDTO apartmentDTO= new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDTO, payment.getApartment());
				paymentDTO.setApartmentDTO(apartmentDTO);
				paymentDTO.setResidentDTO(residentDTO);
				paymentDTOList.add(paymentDTO);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return paymentDTOList;	
	}
	
	public static void update_payment(PaymentDTO paymentDTO) {
		Session session = HibernateUtil.getSession();
		try {
		Transaction transaction=session.beginTransaction();
		
		Query q=session.createQuery("update Payment set status = '"+paymentDTO.getStatus()+"',payment_date='"+paymentDTO.getPayment_date()+"' "
				+ "where payment_id='"+paymentDTO.getPayment_id()+"'");
		q.executeUpdate();

		session.flush();
        session.clear();
		transaction.commit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}	
}
