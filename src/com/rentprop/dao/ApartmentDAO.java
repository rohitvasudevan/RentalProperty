package com.rentprop.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.RentalRequestDTO;
import com.rentprop.entity.Apartment;
import com.rentprop.entity.ApartmentPhoto;
import com.rentprop.entity.RentalRequest;
import com.rentprop.util.HibernateUtil;

public class ApartmentDAO {
	public static ApartmentDTO fetchApartmentById(int apartmentId) {
		ApartmentDTO apartmentDTO = null;
		Session session = HibernateUtil.getSession();
		try {
			Apartment apartment = new Apartment();
			apartment.setApartmentId(apartmentId);
			apartment = session.get(Apartment.class, new Integer(apartmentId));
			if (null != apartment) {
				apartmentDTO = new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDTO, apartment);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return apartmentDTO;
	}
	public int addApartment(ApartmentDTO apartmentDTO) {
		int apartment_id = -1;
		Session session = HibernateUtil.getSession();
		try {
			Apartment apartment = new Apartment();
			BeanUtils.copyProperties(apartment, apartmentDTO);
			Transaction transaction = session.beginTransaction();
			apartment_id = (Integer)session.save(apartment);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}
		return apartment_id;
	}
	public int updateApartment(ApartmentDTO apartmentDTO) {
		int apartment_id = -1;
		Session session = HibernateUtil.getSession();
		try {
			Apartment apartment = new Apartment();
			BeanUtils.copyProperties(apartment, apartmentDTO);

			Transaction transaction = session.beginTransaction();
			session.update(apartment);
			apartment_id = apartment.getApartmentId();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}		
		return apartment_id;
	}
	public List<ApartmentDTO> searchAvailableApartment(ApartmentDTO apartmentDTO) {
		List<Apartment> list_of_apartment = new ArrayList<Apartment>();
		List<ApartmentDTO> resultAptList = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from Apartment where rental_status='available' "
					+ "and no_of_bedroom='" + apartmentDTO.getNoOfBedroom() 
					+ "' and no_of_bathroom='" + apartmentDTO.getNoOfBathroom() + "'");
			list_of_apartment = q.list();
			System.out.println("list size" + list_of_apartment.size());
			resultAptList = new ArrayList<ApartmentDTO>();
			for (Apartment apt : list_of_apartment) {
				ApartmentDTO apartmentDtoFound = new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDtoFound, apt);
				resultAptList.add(apartmentDtoFound);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}
		return resultAptList;
	}

	public List<ApartmentDTO> getAvailableApartment(String photoLoc) {
		List<Apartment> list_of_apartment = new ArrayList<Apartment>();
		List<ApartmentDTO> resultAptList = new ArrayList<ApartmentDTO>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from Apartment where rental_status='available'");
			list_of_apartment = q.list();
			System.out.println("list size" + list_of_apartment.size());
			if (null != list_of_apartment && list_of_apartment.size() > 0) {
				for (Apartment apt : list_of_apartment) {
					ApartmentDTO dto = new ApartmentDTO();
					BeanUtils.copyProperties(dto, apt);

					if (null != apt.getApartmentPhotos() && apt.getApartmentPhotos().size() > 0) {
						List<ApartmentPhoto> apartmentPhotoList = apt.getApartmentPhotos();
						for (ApartmentPhoto apartmentPhoto : apartmentPhotoList) {
							String fileName = apartmentPhoto.getFileName();
							byte[] buffer = apartmentPhoto.getPhoto();
							if (null != buffer) {
								String encryptFileName = apartmentPhoto.getEncryptedName();
								File targetEncryptFile = new File(photoLoc + encryptFileName);
								OutputStream outStream = new FileOutputStream(targetEncryptFile);
								outStream.write(buffer);
								outStream.close();
							}
						}
					}
					resultAptList.add(dto);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}
		return resultAptList;
	}

	public List<ApartmentDTO> searchAllApartments() {
		List<Apartment> list_of_all_apartments = new ArrayList<Apartment>();
		List<ApartmentDTO> apartmenttDtoList = new ArrayList<ApartmentDTO>();
		ApartmentDTO aptDto = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from Apartment");
			list_of_all_apartments = q.list();
			for (Apartment apt : list_of_all_apartments) {
				aptDto = new ApartmentDTO();
				BeanUtils.copyProperties(aptDto, apt);
				apartmenttDtoList.add(aptDto);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}
		return apartmenttDtoList;
	}

	public List authentication_request(RentalRequestDTO RentalRequestDTO) {
		List<RentalRequestDTO> list_of_rental_request = new ArrayList<RentalRequestDTO>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from RentalRequest where "
					+ "emailId='" + RentalRequestDTO.getEmailId()
					+ "' and apartment.apartmentId='" + RentalRequestDTO.getApartmentDTO().getApartmentId() + "'");
			list_of_rental_request = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}
		return list_of_rental_request;
	}
	
	public int addRentalRequest(RentalRequestDTO rentalRequestDTO) {
		Integer rental_id = 0;
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			RentalRequest rentalRequest = new RentalRequest();
			BeanUtils.copyProperties(rentalRequest, rentalRequestDTO);
			Apartment apartment = new Apartment();
			BeanUtils.copyProperties(apartment, rentalRequestDTO.getApartmentDTO());
			rentalRequest.setApartment(apartment);
			rental_id = (Integer) session.save(rentalRequest);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}
		return rental_id;
	}	

	public static List<RentalRequestDTO> search_all_rental_request() {
		List<RentalRequestDTO> list_of_all_apartments = new ArrayList<RentalRequestDTO>();
		List<RentalRequest> rentalRequestList = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery(
					"from RentalRequest "
					+ "where status = 'pending' "
					+ "and apartment.rentalStatus = 'available'");
			rentalRequestList = q.list();
			for (RentalRequest rentalRequest : rentalRequestList) {
				RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
				BeanUtils.copyProperties(rentalRequestDTO, rentalRequest);
				list_of_all_apartments.add(rentalRequestDTO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			HibernateUtil.closeSession(session);
		}
		return list_of_all_apartments;
	}

	public void denyRental(RentalRequestDTO rentalRequestDTO) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();

			Query q = session.createQuery("update RentalRequest "
					+ "set status = '" + rentalRequestDTO.getStatus()
					+ "' where rentalRequestId='" + rentalRequestDTO.getRentalRequestId() + "'");
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
}