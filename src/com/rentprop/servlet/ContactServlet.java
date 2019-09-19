package com.rentprop.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rentprop.dao.ContactUsDAO;
import com.rentprop.dto.ContactUsDTO;
import com.rentprop.util.RentPropUtil;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/contactUsServlet")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag = request.getParameter("flag");
		if (flag.equals("add")) {
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String message = request.getParameter("message");

			ContactUsDTO contactUsDTO = new ContactUsDTO();
			contactUsDTO.setName(name);
			contactUsDTO.setEmailId(email);
			contactUsDTO.setPhone(phone);
			contactUsDTO.setMessage(message);

			ContactUsDAO contactUsDAO = new ContactUsDAO();
			contactUsDAO.addContactUs(contactUsDTO);
			String emailMsg = "Please contact " + name + "<br>Phone: " + phone + "<br>Email: " + email + "<br><br>Message: " + message;
			RentPropUtil.sendMail(email, "Contact Us request", emailMsg);
			request.setAttribute("error", "Thank You for Contacting Us");
			RequestDispatcher rd = request.getRequestDispatcher("user/index.jsp");
			rd.forward(request, response);
		}
	}

}
