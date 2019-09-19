package com.rentprop.servlet;

import java.io.IOException;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rentprop.dao.ApartmentDAO;
import com.rentprop.dao.AppointmentDAO;
import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.AppointmentDTO;
import com.rentprop.dto.RentalRequestDTO;
import com.rentprop.util.RentPropUtil;

/**
 * Servlet implementation class appointmentServlet
 */
@WebServlet("/appointmentServlet")
public class AppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppointmentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");

		if (flag.equals("search")) {
			int apartment_id = Integer.parseInt(request.getParameter("apartment_id"));
			
			ApartmentDTO apartmentDTO = ApartmentDAO.fetchApartmentById(apartment_id);
			session.setAttribute("apartments", apartmentDTO);
			response.sendRedirect("user/appointment.jsp");
		} else if (flag.equals("fetchAddress")) {
			int apartment_id = Integer.parseInt(request.getParameter("apartment_id"));
			ApartmentDTO apartmentDTO = ApartmentDAO.fetchApartmentById(apartment_id);
			session.setAttribute("apartment_address", apartmentDTO);
			response.sendRedirect("user/rentalRequest.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		HttpSession session = request.getSession();

		String flag = request.getParameter("flag");
		if (flag.equals("add")) {
			int apartment_id = Integer.parseInt(request.getParameter("apartment_id"));
			// System.out.println(apartment_id);
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String date = request.getParameter("date");
			String slot = request.getParameter("slot");
			String apt_address = request.getParameter("apt_address");

			AppointmentDTO AppointmentDTO = new AppointmentDTO();
			AppointmentDTO.setFirstname(firstname);
			AppointmentDTO.setLastname(lastname);
			AppointmentDTO.setEmailid(email);
			AppointmentDTO.setPhoneno(phone);
			AppointmentDTO.setAddress(address);
			AppointmentDTO.setDate(date);
			AppointmentDTO.setSlot(slot);

			ApartmentDTO ApartmentDTO = new ApartmentDTO();
			ApartmentDTO.setApartmentId(apartment_id);
			AppointmentDTO.setApartmentDTO(ApartmentDTO);
			int appointment_id = AppointmentDAO.insertNewAppointment(AppointmentDTO);
			AppointmentDTO viewAppointment = AppointmentDAO.findAppointmentById(appointment_id);
			session.setAttribute("viewAppointment", viewAppointment);
			ApartmentDTO apartmentDTO= ApartmentDAO.fetchApartmentById(apartment_id);
			session.setAttribute("apartmentAddress", apartmentDTO);
			
			String emailMsg = "<h1>Appointment Confirmation</h1><br><hr><h2>Hi :" + firstname + " </h2>"
					+ "<br><h2>We have confirmed your Appointment for Apartment:<br> " + apt_address + ",<br> on : " + date
					+ "<br> time: " + slot + " <br>And Your Appointment Id is " + appointment_id + ". 	</h2>";
			String subject = "Appointment Confirmation";
			
			RentPropUtil.sendMail(email, subject, emailMsg);
			
			response.sendRedirect("user/appointmentsuccess.jsp");
		} else if (flag.equals("fetch")) {
			int appointmentid = Integer.parseInt(request.getParameter("appointmentid"));
			String emailid = request.getParameter("emailid");

			AppointmentDTO appointmentDTO = new AppointmentDTO();
			appointmentDTO.setAppointmentId(appointmentid);
			appointmentDTO.setEmailid(emailid);
			session.setAttribute("appointment_id", appointmentid);
			AppointmentDAO appointmentDAO = new AppointmentDAO();
			AppointmentDTO appointmentFound= appointmentDAO.fetch_appointment(appointmentDTO);
			if (null != appointmentFound) {
				
				session.setAttribute("viewAppointment", appointmentFound);
				session.setAttribute("apartmentAddress", appointmentFound.getApartmentDTO());
				
				response.sendRedirect("user/updateAppointment.jsp");
			} else {
				request.setAttribute("error", "Invalid Appointment Id ");
				RequestDispatcher rd = request.getRequestDispatcher("user/changeAppointment.jsp");
				rd.forward(request, response);

			}

		} else if (flag.equals("update")) {
			int appointment_id = (int) session.getAttribute("appointment_id");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String date = request.getParameter("date");
			String slot = request.getParameter("slot");

			AppointmentDTO appointmentDTO = new AppointmentDTO();
			appointmentDTO.setAppointmentId(appointment_id);
			appointmentDTO.setFirstname(firstname);
			appointmentDTO.setLastname(lastname);
			appointmentDTO.setEmailid(email);
			appointmentDTO.setPhoneno(phone);
			appointmentDTO.setAddress(address);
			appointmentDTO.setDate(date);
			appointmentDTO.setSlot(slot);

			AppointmentDAO appointmentDAO = new AppointmentDAO();
			appointmentDAO.update_appointment(appointmentDTO);
			
			AppointmentDTO appointmentFound= appointmentDAO.fetch_appointment(appointmentDTO);
			if (null != appointmentFound) {
				
				session.setAttribute("viewAppointment", appointmentFound);
				session.setAttribute("apartmentAddress", appointmentFound.getApartmentDTO());
				response.sendRedirect("user/appointmentupdated.jsp");
			} else {
				request.setAttribute("error", "Invalid Appointment Id ");
				RequestDispatcher rd = request.getRequestDispatcher("user/updateAppointment.jsp");
				rd.forward(request, response);

			}
		} else if (flag.equals("view")) {
			int appointmentid = Integer.parseInt(request.getParameter("appointmentid"));
			String emailid = request.getParameter("emailid");
			AppointmentDTO appointmentDTO = new AppointmentDTO();
			appointmentDTO.setAppointmentId(appointmentid);
			appointmentDTO.setEmailid(emailid);
			session.setAttribute("appointment_id", appointmentid);
			AppointmentDAO appointmentDAO = new AppointmentDAO();
			AppointmentDTO appointmentFound= appointmentDAO.fetch_appointment(appointmentDTO);
			if (null != appointmentFound) {
				
				session.setAttribute("viewAppointment", appointmentFound);
				session.setAttribute("apartmentAddress", appointmentFound.getApartmentDTO());
				response.sendRedirect("user/appointmentsuccess.jsp");
			} else {
				request.setAttribute("error", "Invalid Appointment Id ");
				RequestDispatcher rd = request.getRequestDispatcher("user/viewAppointment.jsp");
				rd.forward(request, response);

			}
		} else if (flag.equals("viewRentalRequest")) {
			int rental_request_id = Integer.parseInt(request.getParameter("rental_id"));
			String emailid = request.getParameter("emailid");

			RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
			rentalRequestDTO.setRentalRequestId(rental_request_id);
			rentalRequestDTO.setEmailId(emailid);

			session.setAttribute("rental_request_id", rental_request_id);

			AppointmentDAO appointmentDAO = new AppointmentDAO();

			List<RentalRequestDTO> rental_data = appointmentDAO.findRentalRequest(rentalRequestDTO);

			if (rental_data != null && rental_data.size() >= 1) {
				session.setAttribute("rental_data", rental_data);
				response.sendRedirect("user/viewRentalData.jsp");
			}
			else {
				request.setAttribute("error", "Invalid Request Rental Id ");
				RequestDispatcher rd = request.getRequestDispatcher("user/viewRentalRequest.jsp");
				rd.forward(request, response);

			}
		} else if (flag.equals("delete")) {
			int appointmentid = Integer.parseInt(request.getParameter("appointmentid"));
			String emailid = request.getParameter("emailid");
			AppointmentDTO appointmentDTO = new AppointmentDTO();

			appointmentDTO.setAppointmentId(appointmentid);
			appointmentDTO.setEmailid(emailid);
			AppointmentDAO appointmentDAO = new AppointmentDAO();

			AppointmentDTO appointmentFound = appointmentDAO.fetch_appointment(appointmentDTO);

			if (null != appointmentFound) {
				appointmentDAO.delete_appointment(appointmentFound);
				request.setAttribute("error", "Appointment Cancelled");
				RequestDispatcher rd = request.getRequestDispatcher("user/index.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("error", "Invalid Appointment Id ");
				RequestDispatcher rd = request.getRequestDispatcher("user/cancelAppointment.jsp");
				rd.forward(request, response);
			}
		} else if (flag.equals("cancelRentalRequest")) {
			int rental_id = Integer.parseInt(request.getParameter("rental_id"));
			String emailid = request.getParameter("emailid");
			RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
			rentalRequestDTO.setRentalRequestId(rental_id);
			rentalRequestDTO.setEmailId(emailid);
			AppointmentDAO appointmentDAO = new AppointmentDAO();
			List<RentalRequestDTO> rentalRequestDtoFound = appointmentDAO.searchRentalRequest(rentalRequestDTO);
			if (null !=rentalRequestDtoFound && rentalRequestDtoFound.size() >= 1) {
				RentalRequestDTO data = rentalRequestDtoFound.get(0);
				String status = data.getStatus();
				if (status.equals("pending")) {
					appointmentDAO.delete_rental(rentalRequestDTO);
					request.setAttribute("error", "Rental Request Cancelled");
					RequestDispatcher rd = request.getRequestDispatcher("user/index.jsp");
					rd.forward(request, response);
				} else if (status.equals("denied")) {
					request.setAttribute("error", "Your Rental Request is denied, So it can't be Deleted ");
					RequestDispatcher rd = request.getRequestDispatcher("user/index.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("error", "Your Rental Request is Approved, So it can't be Deleted ");
					RequestDispatcher rd = request.getRequestDispatcher("user/index.jsp");
					rd.forward(request, response);
				}
			} else {
				request.setAttribute("error", "Invalid Rental Request Id ");
				RequestDispatcher rd = request.getRequestDispatcher("user/cancelRentalRequest.jsp");
				rd.forward(request, response);
			}
		}
	}
}
