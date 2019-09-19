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

/**
 * Servlet implementation class contactUsController
 */
@WebServlet("/contactUsController")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	String flag=request.getParameter("flag");
	if(flag.equals("add")){
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String message=request.getParameter("message");
		
		ContactUsDTO contactUsDTO=new ContactUsDTO();
		contactUsDTO.setName(name);
		contactUsDTO.setEmail(email);
		contactUsDTO.setPhone(phone);
		contactUsDTO.setMessage(message);
		
		ContactUsDAO contactUsDAO=new ContactUsDAO();
		contactUsDAO.addContactUs(contactUsDTO);
		request.setAttribute("error", "Thank You for Contacting Us");
		RequestDispatcher rd=request.getRequestDispatcher("user/index.jsp");
		rd.forward(request, response);	
	}
	}

}
