package com.rentprop.servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rentprop.dao.ApartmentDAO;
import com.rentprop.dao.LoginDAO;
import com.rentprop.dao.ResidentDAO;
import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.LoginDTO;
import com.rentprop.dto.PaymentDTO;
import com.rentprop.dto.RentalRequestDTO;
import com.rentprop.dto.ResidentDTO;
import com.rentprop.util.AddResidentValidationUtil;
import com.rentprop.util.RentPropUtil;

@WebServlet("/addResidentServlet")
public class ResidentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResidentServlet() {
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
			List<ResidentDTO> residentsList = ResidentDAO.findAllResidentTypeUser();

			session.setAttribute("search_residents", residentsList);
			response.sendRedirect("admin/editDeleteResident.jsp");
		}

		else if (flag.equals("search_apartment")) {

			ApartmentDAO apartmentDAO = new ApartmentDAO();
			List<ApartmentDTO> list_of_all_apartments = apartmentDAO.searchAllApartments();
			session.setAttribute("search_apartments_address1", list_of_all_apartments);
			response.sendRedirect("admin/addResident.jsp");

		} else if (flag.equals("search_request")) {
			List<RentalRequestDTO> list_of_all_request = ApartmentDAO.search_all_rental_request();
			session.setAttribute("list_of_all_request", list_of_all_request);
			response.sendRedirect("admin/manageRequest.jsp");
		} else if (flag.equals("edit_residents")) {
			String username = request.getParameter("username");
			
			ResidentDAO residentDAO = new ResidentDAO();
			List<ResidentDTO> list_of_residents=residentDAO.findResidentByUsername(username);
			
			session.setAttribute("edit_residents", list_of_residents);
			response.sendRedirect("admin/editResident.jsp");
		} else if (flag.equals("edit")) {
			int resident_id=Integer.parseInt(request.getParameter("resident_id"));
			ResidentDTO resident=ResidentDAO.findResidentIdByResidentId(resident_id);
			
			session.setAttribute("edit_residents1", resident);
			response.sendRedirect("user/editProfile.jsp");
		} else if (flag.equals("delete_resident")) {
			String username = request.getParameter("username");
			
			ResidentDAO residentDAO = new ResidentDAO();
			List list = residentDAO.findResidentByUsername(username);

			if(list != null && list.size()>=1) {
				ResidentDTO residentDTO=(ResidentDTO) list.get(0);
				
				LoginDAO loginDAO = new LoginDAO();
				
				loginDAO.delete(residentDTO.getLoginDto());
				List<ResidentDTO> list_of_all_residents=ResidentDAO.findAllResidentTypeUser();
				session.setAttribute("search_residents", list_of_all_residents);
				
				request.setAttribute("error", "Resident Deleted");
				RequestDispatcher rd=request.getRequestDispatcher("admin/editDeleteResident.jsp");
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag = request.getParameter("flag");
		HttpSession session = request.getSession();
		if (flag.equals("add")) {
			int apartmentID = Integer.parseInt(request.getParameter("apartmentID"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String emailId = request.getParameter("emailId");
			Long phoneNo = Long.parseLong(request.getParameter("phoneNo"));
			String userName = request.getParameter("userId");
			String password = request.getParameter("password");
			String userType = request.getParameter("userType");

			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUsername(userName);
			loginDTO.setPassword(password);
			loginDTO.setUsertype(userType);
			loginDTO.setFirstName(firstName);
			loginDTO.setLastName(lastName);
			loginDTO.setEmailId(emailId);
			loginDTO.setPhoneNumber(BigInteger.valueOf(phoneNo));

			ResidentDTO residentDTO = new ResidentDTO();
			residentDTO.setLoginDto(loginDTO);

			ApartmentDTO apartmentDTO = new ApartmentDTO();
			apartmentDTO.setApartmentId(apartmentID);
			residentDTO.setApartmentDTO(apartmentDTO);

			ResidentDAO residentDAO = new ResidentDAO();

			AddResidentValidationUtil AddResidentValidationUtil = new AddResidentValidationUtil();
			String validateMandatory = AddResidentValidationUtil.mandatory(residentDTO);
			String matchFields = AddResidentValidationUtil.parameters_resident(loginDTO);
			if (validateMandatory.equals("All fields are Mandatory")
					|| validateMandatory.equals("First Name is mandatory!")
					|| validateMandatory.equals("Last Name is mandatory!")
					|| validateMandatory.equals("Email Id is mandatory!")
					|| validateMandatory.equals("Address is mandatory!")
					|| validateMandatory.equals("User Id is mandatory!")
					|| validateMandatory.equals("Password is mandatory!")) {
				request.setAttribute("error", validateMandatory);
				RequestDispatcher rd = request.getRequestDispatcher("admin/addResident.jsp");
				rd.forward(request, response);
			} else if (matchFields.equals("Password should be alph-numeric")
					|| matchFields.equals("User Id should be alpha-numeric")
					|| matchFields.equals("Last Name should be alphabets only!")
					|| matchFields.equals("First Name should be alphabets only!")) {
				System.out.println(matchFields);
				request.setAttribute("error", matchFields);
				RequestDispatcher rd = request.getRequestDispatcher("admin/addResident.jsp");
				rd.forward(request, response);
			} else {
				ResidentDTO residentDtoFound = ResidentDAO.findResidentIdByUserName(userName);
				List list = residentDAO.authentication_email(residentDTO);
				if (list != null && list.size() >= 1) {
					request.setAttribute("error", "Email Id already Registered");
					RequestDispatcher rd = request.getRequestDispatcher("admin/addResident.jsp");
					rd.forward(request, response);
				} else if (residentDtoFound != null) {
					request.setAttribute("error", "User Id already Registered");
					RequestDispatcher rd = request.getRequestDispatcher("admin/addResident.jsp");
					rd.forward(request, response);
				} else {
					LoginDAO loginDao = new LoginDAO();
					int login_id = loginDao.add_login(loginDTO);
					residentDTO.setLoginDto(loginDTO);
					ApartmentDTO user = residentDAO.findAptById(apartmentDTO);
					if (user != null) {
						String address = user.getAddress() + ", " + user.getCity() + ", " + user.getState() + " - "
								+ user.getZipCode();
						residentDTO.setAddress(address);

						int resident_id = residentDAO.addResident(residentDTO);
						residentDAO.update_rental_status(residentDTO);
						PaymentDTO paymentDTO = new PaymentDTO();
						Date date = new Date();
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String currentTime = sdf.format(date);
						paymentDTO.setDate(currentTime);
						// paymentDTO.setRent(ApartmentDTO.getPrice());
						paymentDTO.setStatus("pending");

						ResidentDTO residentDto = new ResidentDTO();
						residentDto.setResidentId(resident_id);
						paymentDTO.setResidentDTO(residentDto);

						ApartmentDTO addApartmentVO1 = new ApartmentDTO();
						addApartmentVO1.setApartmentId(apartmentID);
						paymentDTO.setApartmentDTO(addApartmentVO1);
						residentDAO.addPayment(paymentDTO);

						ApartmentDAO apartmentDAO = new ApartmentDAO();
						List<ApartmentDTO> list_of_all_apartments = apartmentDAO.searchAllApartments();
						session.setAttribute("search_apartments_address1", list_of_all_apartments);

					}
					String sentToAddress = residentDTO.getLoginDto().getEmailId();
					String username = residentDTO.getLoginDto().getUsername();
					String pwd = residentDTO.getLoginDto().getPassword();
					
					String emailMsg = "<h1>RentProp Login Credentials</h1><br><hr><h2>This is your User Id :" + username
							+ " </h2>" + "<br><h2>and this is Your Password :" + pwd + "</h2>";
					String subject = "RentProp Login Credentials";
					
					RentPropUtil.sendMail(sentToAddress, subject, emailMsg);

					request.setAttribute("error", "Resident Added");
					RequestDispatcher rd = request.getRequestDispatcher("admin/addResident.jsp");
					rd.forward(request, response);
				}
			}
		}

		if (flag.equals("update")) {
			int residentId = Integer.parseInt(request.getParameter("residentId"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String emailId = request.getParameter("emailId");
			Long phoneNo = Long.parseLong(request.getParameter("phoneNo"));

			String userId = request.getParameter("userId");
			String password = request.getParameter("password");

			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUsername(userId);
			loginDTO.setPassword(password);
			loginDTO.setFirstName(firstName);
			loginDTO.setLastName(lastName);
			loginDTO.setEmailId(emailId);
			loginDTO.setPhoneNumber(BigInteger.valueOf(phoneNo));
			loginDTO.setUsertype("resident");

			ResidentDTO residentDTO = new ResidentDTO();
			residentDTO.setResidentId(residentId);
			residentDTO.setLoginDto(loginDTO);

			LoginDAO loginDAO = new LoginDAO();

			AddResidentValidationUtil residentValidationUtil = new AddResidentValidationUtil();
			String validateMandatory = residentValidationUtil.mandatory(residentDTO);
			String matchFields = residentValidationUtil.parameters_resident(loginDTO);
			if (validateMandatory.equals("All fields are Mandatory")
					|| validateMandatory.equals("First Name is mandatory!")
					|| validateMandatory.equals("Last Name is mandatory!")
					|| validateMandatory.equals("Email Id is mandatory!")
					|| validateMandatory.equals("Address is mandatory!")
					|| validateMandatory.equals("User Id is mandatory!")
					|| validateMandatory.equals("Password is mandatory!")) {
				request.setAttribute("error", validateMandatory);
				RequestDispatcher rd = request.getRequestDispatcher("admin/editResident.jsp");
				rd.forward(request, response);
			}

			else if (matchFields.equals("Password should be alph-numeric")
					|| matchFields.equals("User Id should be alpha-numeric")
					|| matchFields.equals("Last Name should be alphabets only!")
					|| matchFields.equals("First Name should be alphabets only!")) {
				System.out.println(matchFields);
				request.setAttribute("error", matchFields);
				RequestDispatcher rd = request.getRequestDispatcher("admin/editResident.jsp");
				rd.forward(request, response);
			} else {

				
				loginDAO.updatelogin(loginDTO);

				List<ResidentDTO> list_of_all_residents = ResidentDAO.findAllResidentTypeUser();
				session.setAttribute("search_residents", list_of_all_residents);
				request.setAttribute("error", "Resident Details Updated");
				RequestDispatcher rd = request.getRequestDispatcher("admin/editDeleteResident.jsp");
				rd.forward(request, response);
			}
		}
		if(flag.equals("updateProfileByResident")){
			int residentId=Integer.parseInt(request.getParameter("residentId"));
			String firstName=request.getParameter("firstName");
			String lastName=request.getParameter("lastName");
			
			BigInteger phoneNo=BigInteger.valueOf(Long.parseLong(request.getParameter("phoneNo")));
			String username=request.getParameter("username");
			
			ResidentDTO residentDTO = new ResidentDTO();
			residentDTO.setResidentId(residentId);
			
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUsername(username);;
			loginDTO.setFirstName(firstName);
			loginDTO.setLastName(lastName);
			loginDTO.setPhoneNumber(phoneNo);
			ResidentDAO residentDAO=new ResidentDAO();
			LoginDAO.updateProfile(loginDTO);
			List<ResidentDTO> list_of_all_residents=residentDAO.findResidentByUsername(username);
			session.setAttribute("search_residents", list_of_all_residents);
			request.setAttribute("error", "Profile updated successfully");
			RequestDispatcher rd=request.getRequestDispatcher("user/index.jsp");
			rd.forward(request, response);
			}
	}

}
