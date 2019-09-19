package com.rentprop.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rentprop.dao.LoginDAO;
import com.rentprop.dao.ResidentDAO;
import com.rentprop.dto.LoginDTO;
import com.rentprop.dto.ResidentDTO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag = request.getParameter("flag");
		HttpSession session = request.getSession();
		if (flag.equals("logout")) {
			session.invalidate();
			response.sendRedirect("user/index.jsp");
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
		if (flag.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			LoginDTO loginDto = new LoginDTO();
			loginDto.setUsername(username);
			loginDto.setPassword(password);

			LoginDAO loginDAO = new LoginDAO();
			LoginDTO loginDtoFound = loginDAO.findByUserNameAndPassword(loginDto);

			if (loginDtoFound != null) {
				String type = loginDtoFound.getUsertype();

				session.setAttribute("user_name", username);
				session.setAttribute("userType", type);
				session.setAttribute("firstName", loginDtoFound.getFirstName());

				if (type.equalsIgnoreCase("admin")) {
					session.setAttribute("type", "admin");
					response.sendRedirect("admin/index.jsp");
				} else if (type.equalsIgnoreCase("resident")) {
					ResidentDTO residentDTO = ResidentDAO.findResidentIdByUserName(username);
					int resident_id = residentDTO.getResidentId();
					session.setAttribute("type", "resident");
					session.setAttribute("resident_id", resident_id);
					response.sendRedirect("user/index.jsp");
				} else {
					request.setAttribute("error", "Not yet Registered");
					RequestDispatcher rd = request.getRequestDispatcher("user/login.jsp");
					rd.forward(request, response);
				}
			} else {
				request.setAttribute("error", "Invalid Login Credentials");
				RequestDispatcher rd = request.getRequestDispatcher("user/login.jsp");
				rd.forward(request, response);
			}

		} else {
			request.setAttribute("error", "Invalid Login Credentials");
			RequestDispatcher rd = request.getRequestDispatcher("user/login.jsp");
			rd.forward(request, response);

		}
	}
}
