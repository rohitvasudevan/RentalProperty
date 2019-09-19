package com.rentprop.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rentprop.dao.ApartmentDAO;
import com.rentprop.dto.ApartmentDTO;

/**
 * Servlet implementation class allProperties
 */
@WebServlet("/allProperties")
public class PropertiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PropertiesServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in all properties get");
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");
		if (flag.equals("fetch_properties")) {
			ApartmentDAO apartmentDAO = new ApartmentDAO();
			String filePath = getServletContext().getRealPath(request.getServletPath());
			int path = filePath.lastIndexOf('\\');
			String path1= filePath.substring(0, path) +"\\img\\";
			List<ApartmentDTO> list_of_apartment = apartmentDAO.getAvailableApartment(path1);
			session.setAttribute("fetch_properties", list_of_apartment);
			response.sendRedirect("user/allProperties.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In all properties post");
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");
		if (flag.equals("fetch_properties1")) {
			int bed = Integer.parseInt(request.getParameter("bed"));
			int bath = Integer.parseInt(request.getParameter("bath"));

			ApartmentDTO apartmentDTO = new ApartmentDTO();
			apartmentDTO.setNoOfBedroom(bed);
			apartmentDTO.setNoOfBathroom(bath);

			ApartmentDAO apartmentDAO = new ApartmentDAO();
			List<ApartmentDTO> list_of_apartment = apartmentDAO.searchAvailableApartment(apartmentDTO);
			if (list_of_apartment.size() > 0) {
				session.setAttribute("fetch_properties", list_of_apartment);
				response.sendRedirect("user/allProperties.jsp");
			} else {
				List<ApartmentDTO> list_of_apartment1 = apartmentDAO.searchAvailableApartment(apartmentDTO);
				session.setAttribute("fetch_properties", list_of_apartment1);
				request.setAttribute("error", "Apartments Not Found");
				RequestDispatcher rd = request.getRequestDispatcher("user/allProperties.jsp");
				rd.forward(request, response);
			}
		}
	}
}
