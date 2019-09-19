package com.rentprop.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.ApartmentPhotoDTO;
import com.rentprop.entity.Apartment;
import com.rentprop.entity.ApartmentPhoto;
import com.rentprop.util.HibernateUtil;

public class ApartmentPhotoDAO {
	public int add_photos(ApartmentPhotoDTO apartmentPhotoDTO) {
		int photoId = -1;
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			ApartmentPhoto apartmentPhoto = new ApartmentPhoto();
			BeanUtils.copyProperties(apartmentPhoto, apartmentPhotoDTO);
			ApartmentDTO apartmentDTO = apartmentPhotoDTO.getApartmentDTO();
			Apartment apartment = new Apartment();
			apartment.setApartmentId(apartmentPhotoDTO.getApartmentDTO().getApartmentId());
			apartmentPhoto.setApartment(apartment);
			session.save(apartmentPhoto);
			photoId = apartmentPhotoDTO.getPhotoId();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return photoId;
	}

	public List<ApartmentPhotoDTO> search_all_apartments_photos() {
		List<ApartmentPhotoDTO> apartmentPhotoDTOList = new ArrayList<ApartmentPhotoDTO>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from ApartmentPhoto");
			List<ApartmentPhoto> apartmentPhotoList = q.list();
			for (ApartmentPhoto apartmentPhoto : apartmentPhotoList) {
				ApartmentPhotoDTO apartmentPhotoDTOTemp = new ApartmentPhotoDTO();
				BeanUtils.copyProperties(apartmentPhotoDTOTemp, apartmentPhoto);
				ApartmentDTO apartmentDtoTemp = new ApartmentDTO();
				BeanUtils.copyProperties(apartmentDtoTemp, apartmentPhoto.getApartment());
				apartmentPhotoDTOTemp.setApartmentDTO(apartmentDtoTemp);
				apartmentPhotoDTOList.add(apartmentPhotoDTOTemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return apartmentPhotoDTOList;
	}

	public void delete_apartment_photos(ApartmentPhotoDTO apartmentPhotoDTO) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			ApartmentPhoto apartmentPhoto = session.get(ApartmentPhoto.class, apartmentPhotoDTO.getPhotoId());
			session.delete(apartmentPhoto);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List<ApartmentPhotoDTO> fetchPhotoByAptId(int apartmentId) {
		List<ApartmentPhotoDTO> apartmentPhotoDTOList = new ArrayList<ApartmentPhotoDTO>();
		Session session = HibernateUtil.getSession();
		try{
			Query q=session.createQuery("from ApartmentPhoto where apartment.apartmentId='"+apartmentId+"'");
			List<ApartmentPhoto> apartmentPhotoList = q.list();
			for (ApartmentPhoto apartmentPhoto : apartmentPhotoList) {
				ApartmentPhotoDTO apartmentPhotoDTO = new ApartmentPhotoDTO();
				BeanUtils.copyProperties(apartmentPhotoDTO, apartmentPhoto);
				apartmentPhotoDTOList.add(apartmentPhotoDTO);
			}
		} catch(Exception ex) {
				ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return apartmentPhotoDTOList;
	}
}
