package com.rentprop.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rentprop.dao.LoginDAO;
import com.rentprop.dto.LoginDTO;
import com.rentprop.util.RentPropConstant;
import com.rentprop.util.RentPropUtil;

/**
 * Servlet implementation class ForgotPasswordServlet
 */
@WebServlet("/forgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForgotPasswordServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	int OTP;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");
		if (flag.equals("email")) {
			String email = request.getParameter("email");

			LoginDTO loginDto = new LoginDTO();
			loginDto.setEmailId(email);

			LoginDAO loginDAO = new LoginDAO();
			List<LoginDTO> loginDtoList = loginDAO.forgetPassword(loginDto);

			if (loginDtoList != null && loginDtoList.size() >= 1) {
				session.setAttribute("email", loginDto.getEmailId());

				OTP = RentPropUtil.generateOTP(5);
				
				String subject = "OTP for Forgot password";
				String emailMsg = "<h1>This is your OTP " + OTP + " Code</h1>";
				RentPropUtil.sendMail(email, subject, emailMsg);

				session.setAttribute("OTP", OTP);
				response.sendRedirect("user/newPassword.jsp");
			} else {
				response.sendRedirect("admin/email.jsp");
			}
		} else if (flag.equals("changepassword")) {
			int otp2 = (Integer) session.getAttribute("OTP");
			int otp_value = Integer.parseInt(request.getParameter("otp"));
			System.out.println("user entered" + otp_value);
			String newPassword = request.getParameter("newPassword");

			if (otp_value == otp2) {
				String email = (String) session.getAttribute("email");

				LoginDTO loginDto = new LoginDTO();
				loginDto.setEmailId(email);
				LoginDAO loginDao = new LoginDAO();
				List<LoginDTO> LoginDtoList = loginDao.forgetPassword(loginDto);
				if (LoginDtoList != null && LoginDtoList.size() >= 1) {

					loginDto = (LoginDTO) LoginDtoList.get(0);
					loginDto.setPassword(newPassword);
					loginDao.updatePassword(loginDto);

					response.sendRedirect("user/login.jsp");
				}
			} else {
				request.setAttribute("error", "Invalid OTP");
				RequestDispatcher rd = request.getRequestDispatcher("user/newPassword.jsp");
				rd.forward(request, response);
			}
		}
	}
}
