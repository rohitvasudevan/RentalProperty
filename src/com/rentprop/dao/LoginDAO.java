package com.rentprop.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rentprop.dto.LoginDTO;
import com.rentprop.dto.ResidentDTO;
import com.rentprop.entity.User;
import com.rentprop.util.HibernateUtil;

public class LoginDAO {

	public LoginDTO findByUserNameAndPassword(LoginDTO loginDto) {
		LoginDTO loginDtoFound = null;
		List<User> list_of_user = new ArrayList<User>();
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from User where username='" + loginDto.getUsername() + "' and password='"
					+ loginDto.getPassword() + "'");
			list_of_user = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		if (null != list_of_user && list_of_user.size() > 0) {
			for (User user : list_of_user) {
				try {
					loginDtoFound = new LoginDTO();
					BeanUtils.copyProperties(loginDtoFound, user);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return loginDtoFound;
	}

	public void delete(LoginDTO loginDto) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			User user = new User();
			BeanUtils.copyProperties(user, loginDto);
			ResidentDTO residentDTO = ResidentDAO.findResidentIdByUserName(loginDto.getUsername());
			int apartmentId = residentDTO.getApartmentDTO().getApartmentId();
			session.delete(user);
			
			Query query = session.createSQLQuery("update Apartment set RENTAL_STATUS = '" + "available" + "' where APARTMENT_ID = '" + apartmentId + "'");
			query.executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void updatelogin(LoginDTO loginDto) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();

			User updatedUser = new User();
			BeanUtils.copyProperties(updatedUser, loginDto);
			session.update(updatedUser);
			session.flush();
			session.clear();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int add_login(LoginDTO loginDTO) {
		User user = new User();
		Session session = HibernateUtil.getSession();
		try {
			BeanUtils.copyProperties(user, loginDTO);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		int login_id = -1;
		try {
			Transaction transaction = session.beginTransaction();
			session.save(user);
			session.flush();
			transaction.commit();
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return login_id;
	}

	public List<LoginDTO> forgetPassword(LoginDTO loginDto) {
		List<LoginDTO> loginDtoList = null;
		List<User> userList = null;
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("from User where emailId = '" + loginDto.getEmailId() + "'");
			userList = q.list();
			if (null != userList && userList.size() > 0) {
				loginDtoList = new ArrayList<LoginDTO>();
				for (User user : userList) {
					LoginDTO loginDtoTemp = new LoginDTO();
					BeanUtils.copyProperties(loginDtoTemp, user);
					loginDtoList.add(loginDtoTemp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return loginDtoList;
	}

	public void updatePassword(LoginDTO logginDto) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();

			Query q = session.createQuery("update User set password = '" + logginDto.getPassword()
					+ "' where username='" + logginDto.getUsername() + "'");
			q.executeUpdate();
			session.flush();
			session.clear();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	
	public static void updateProfile(LoginDTO loginDTO) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();

			User user = null;
			user = session.get(User.class, loginDTO.getUsername());
			if (null != user) {
				user.setFirstName(loginDTO.getFirstName());
				user.setLastName(loginDTO.getLastName());
				user.setPhoneNumber(loginDTO.getPhoneNumber());
				session.update(user);
			}
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
