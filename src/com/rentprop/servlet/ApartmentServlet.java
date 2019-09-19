package com.rentprop.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rentprop.dao.ApartmentDAO;
import com.rentprop.dao.ApartmentPhotoDAO;
import com.rentprop.dao.AppointmentDAO;
import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.ApartmentPhotoDTO;
import com.rentprop.dto.RentalRequestDTO;

/**
 * Servlet implementation class ApartmentServlet
 */
@WebServlet("/addApartmentServlet")
@MultipartConfig
public class ApartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApartmentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");
		if (flag.equals("search")) {
			ApartmentDAO apartmentDAO = new ApartmentDAO();
			List<ApartmentDTO> list_of_all_apartments = apartmentDAO.searchAllApartments();
			session.setAttribute("search_apartments", list_of_all_apartments);
			response.sendRedirect("admin/manageApartment.jsp");
		} 
		else if(flag.equals("delete_rental_request")){
			int residentId=Integer.parseInt(request.getParameter("rentalRequestId"));
			RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
			rentalRequestDTO.setRentalRequestId(residentId);
			rentalRequestDTO.setStatus("denied");
			ApartmentDAO apartmentDAO=new ApartmentDAO();
			apartmentDAO.denyRental(rentalRequestDTO);
			request.setAttribute("error", "Rental Request Denied");
			List<RentalRequestDTO> list_of_all_request=apartmentDAO.search_all_rental_request();
			session.setAttribute("list_of_all_request", list_of_all_request);
			RequestDispatcher rd=request.getRequestDispatcher("admin/manageRequest.jsp");
			rd.forward(request, response);
		}
		else if(flag.equals("edit_apartments")){
			int apartmentId=Integer.parseInt(request.getParameter("apartment_id"));
			ApartmentDTO apartmentDTO=new ApartmentDTO();
			ApartmentDAO apartmentDAO=new ApartmentDAO();
			apartmentDTO = apartmentDAO.fetchApartmentById(apartmentId);
			session.setAttribute("edit_apartments", apartmentDTO);
			response.sendRedirect("admin/editApartment.jsp");
		}
		else if(flag.equals("list")){
			int apartmentId=Integer.parseInt(request.getParameter("apartment_id"));
			ApartmentDTO ApartmentDTO=new ApartmentDTO();
			ApartmentDTO.setApartmentId(apartmentId);
			
			ApartmentPhotoDTO ApartmentPhotoDTO=new ApartmentPhotoDTO();
			ApartmentPhotoDTO.setApartmentDTO(ApartmentDTO);
			
			ApartmentDAO apartmentDAO=new ApartmentDAO();
			ApartmentDTO apartmentDTO=apartmentDAO.fetchApartmentById(apartmentId);
			List<ApartmentPhotoDTO> list_of_apartment_photos=ApartmentPhotoDAO.fetchPhotoByAptId(apartmentId);
			session.setAttribute("list_of_apartment_photos", list_of_apartment_photos);
			session.setAttribute("edit_apartments1", apartmentDTO);
			response.sendRedirect("user/viewPropertyDetail.jsp");
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");
		if (flag.equals("add")) {

			String rental_status = request.getParameter("rental_status");
			String apartmentAddress = request.getParameter("address");
			int bedroom = Integer.parseInt(request.getParameter("bedroom"));
			int bathroom = Integer.parseInt(request.getParameter("bathroom"));
			String amenities = request.getParameter("amenities");
			Long zipCode = Long.parseLong(request.getParameter("zipCode"));
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Float price = Float.parseFloat(request.getParameter("price"));

			ApartmentDTO apartmentDTO = new ApartmentDTO();
			apartmentDTO.setAddress(apartmentAddress);
			apartmentDTO.setNoOfBedroom(bedroom);
			apartmentDTO.setNoOfBathroom(bathroom);
			apartmentDTO.setAminities(amenities);
			apartmentDTO.setZipCode(zipCode);
			apartmentDTO.setCity(city);
			apartmentDTO.setState(state);
			apartmentDTO.setPrice(price);
			apartmentDTO.setRentalStatus(rental_status);
			ApartmentDAO apartmentDAO = new ApartmentDAO();
			int apartment_id = apartmentDAO.addApartment(apartmentDTO);
			request.setAttribute("error", "Apartment Details Added");
			RequestDispatcher rd = request.getRequestDispatcher("admin/addApartment.jsp");
			rd.forward(request, response);
		} else if (flag.equals("addRental")) {
			int apartment_id = Integer.parseInt(request.getParameter("apartmentId"));
			String first_name = request.getParameter("firstname");
			String last_name = request.getParameter("lastname");
			String email_id = request.getParameter("email");
			Long phone = Long.parseLong(request.getParameter("phone"));
			String address = request.getParameter("address");

			RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
			rentalRequestDTO.setFirstName(first_name);
			rentalRequestDTO.setLastName(last_name);
			rentalRequestDTO.setEmailId(email_id);
			rentalRequestDTO.setPhoneNumber(phone);
			rentalRequestDTO.setAddress(address);
			rentalRequestDTO.setStatus("pending");

			ApartmentDTO apartmentDTO = new ApartmentDTO();
			apartmentDTO.setApartmentId(apartment_id);
			rentalRequestDTO.setApartmentDTO(apartmentDTO);

			ApartmentDAO apartmentDAO = new ApartmentDAO();
			List list = apartmentDAO.authentication_request(rentalRequestDTO);

			if (list.size() == 0) {
				int rental_id = apartmentDAO.addRentalRequest(rentalRequestDTO);
				rentalRequestDTO.setRentalRequestId(rental_id);
				session.setAttribute("rental_request_id", rental_id);
				AppointmentDAO AppointmentDAO = new AppointmentDAO();
				List<RentalRequestDTO> rental_data = AppointmentDAO.getRentalData(rentalRequestDTO);
				session.setAttribute("rental_data", rental_data);
//				 sendMail(email_id,rental_id,first_name,address);

				response.sendRedirect("user/viewRentalData.jsp");

			} else {
				request.setAttribute("error", "You already have Rental Request for this Apartment");
				RequestDispatcher rd = request.getRequestDispatcher("user/index.jsp");
				rd.forward(request, response);
			}
		} else if(flag.equals("update")){
			int apartment_id=Integer.parseInt(request.getParameter("apartmentId"));
			String rentalStatus = request.getParameter("status");
			String apartmentAddress=request.getParameter("address");
			int bedroom=Integer.parseInt(request.getParameter("bedroom"));
			int bathroom=Integer.parseInt(request.getParameter("bathroom"));
			String amenities=request.getParameter("amenities");
			Long zipCode=Long.parseLong(request.getParameter("zipCode"));
			String city=request.getParameter("city");
			String state=request.getParameter("state");
			Float price=Float.parseFloat(request.getParameter("price"));
			
			ApartmentDTO apartmentDTO = new ApartmentDTO();
			apartmentDTO.setApartmentId(apartment_id);
			apartmentDTO.setRentalStatus(rentalStatus);
			apartmentDTO.setAddress(apartmentAddress);
			apartmentDTO.setNoOfBedroom(bedroom);
			apartmentDTO.setNoOfBathroom(bathroom);
			apartmentDTO.setAminities(amenities);
			apartmentDTO.setZipCode(zipCode);
			apartmentDTO.setCity(city);
			apartmentDTO.setState(state);
			apartmentDTO.setPrice(price);
			
			ApartmentDAO addApartmentDAO=new ApartmentDAO();
			addApartmentDAO.updateApartment(apartmentDTO);
			
			List<ApartmentDTO> list_of_all_apartments=addApartmentDAO.searchAllApartments();
			session.setAttribute("search_apartments", list_of_all_apartments);
			request.setAttribute("error", "Apartment Details Updated");
			RequestDispatcher rd=request.getRequestDispatcher("admin/manageApartment.jsp");
			rd.forward(request, response);
		}

	}
}
