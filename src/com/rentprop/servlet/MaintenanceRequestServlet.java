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

import com.rentprop.dao.MaintenanceRequestDAO;
import com.rentprop.dao.ResidentDAO;
import com.rentprop.dto.MaintenanceRequestDTO;
import com.rentprop.dto.ReplyDTO;
import com.rentprop.dto.ResidentDTO;
import com.rentprop.util.RentPropUtil;

/**
 * Servlet implementation class MaintenanceRequestServlet
 */
@WebServlet("/maintenanceRequestServlet")
public class MaintenanceRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceRequestServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag=request.getParameter("flag");
		HttpSession session=request.getSession();
		if(flag.equals("search")){
			
			MaintenanceRequestDAO maintenanceRequestDAO=new MaintenanceRequestDAO();
			List<MaintenanceRequestDTO> list_of_all_services=maintenanceRequestDAO.search_all_services();
			session.setAttribute("search_services", list_of_all_services);
			response.sendRedirect("admin/manageServices.jsp");
			
		}
		else if(flag.equals("delete")){
			int serviceId=Integer.parseInt(request.getParameter("service_id"));
			MaintenanceRequestDTO maintenanceRequestDTO=new MaintenanceRequestDTO();
			maintenanceRequestDTO.setServiceId(serviceId);
			MaintenanceRequestDAO maintenanceRequestDAO=new MaintenanceRequestDAO();
			maintenanceRequestDAO.deleteMaintRequest(maintenanceRequestDTO);
			List<MaintenanceRequestDTO> list_of_all_services=maintenanceRequestDAO.search_all_services();
			session.setAttribute("search_services", list_of_all_services);
			request.setAttribute("error", "Requested Service Deleted");
			RequestDispatcher rd=request.getRequestDispatcher("admin/manageServices.jsp");
			rd.forward(request, response);
		}
		else if(flag.equals("fetch")){
			int serviceId=Integer.parseInt(request.getParameter("service_id"));
			int residentId=Integer.parseInt(request.getParameter("resident_id"));
			
			MaintenanceRequestDAO maintenanceRequestDAO=new MaintenanceRequestDAO();
			MaintenanceRequestDTO maintenanceRequest=maintenanceRequestDAO.findServiceReqById(serviceId);
			
			ResidentDTO resident=ResidentDAO.findResidentIdByResidentId(residentId);
			
			session.setAttribute("resident", resident);
			session.setAttribute("maintenanceRequest", maintenanceRequest);
			response.sendRedirect("admin/replyServiceRequest.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String flag=request.getParameter("flag");
		if(flag.equals("add")){
			String serviceType=request.getParameter("serviceType");
			String serviceDescription=request.getParameter("serviceDescription");
			
			MaintenanceRequestDTO maintenanceRequestDTO=new MaintenanceRequestDTO();
			maintenanceRequestDTO.setServiceType(serviceType);
			maintenanceRequestDTO.setServiceDescription(serviceDescription);
			int resident_id=(int) session.getAttribute("resident_id");
			ResidentDTO residentDTO=new ResidentDTO(); 
			residentDTO.setResidentId(resident_id);
			maintenanceRequestDTO.setResidentDTO(residentDTO);
			MaintenanceRequestDAO maintenanceRequestDAO=new MaintenanceRequestDAO();
			maintenanceRequestDAO.addService(maintenanceRequestDTO);
			request.setAttribute("error", "Request for a service is submitted");
			RequestDispatcher rd=request.getRequestDispatcher("user/index.jsp");
			rd.forward(request, response);
		}
		else if(flag.equals("add_reply")){
			int serviceId=Integer.parseInt(request.getParameter("service_id"));
			int residentId=Integer.parseInt(request.getParameter("resident_id"));
			String emailId=request.getParameter("emailId");
			String replyMessage=request.getParameter("replyMessage");
			
			ReplyDTO replyDTO=new ReplyDTO();
			replyDTO.setReplyMessage(replyMessage);
			
			MaintenanceRequestDTO maintenanceRequestDTO=new MaintenanceRequestDTO();
			maintenanceRequestDTO.setServiceId(serviceId);
			replyDTO.setMaintenanceRequestDTO(maintenanceRequestDTO);
			
			ResidentDTO residentDTO=new ResidentDTO();
			residentDTO.setResidentId(residentId);
			replyDTO.setResidentDTO(residentDTO);
			
			MaintenanceRequestDAO maintenanceRequestDAO=new MaintenanceRequestDAO();
			maintenanceRequestDAO.addReply(replyDTO);
			
			System.out.println(replyMessage + emailId);
			String subject = "RE:Your requested service";
			RentPropUtil.sendMail(emailId, subject, replyMessage);
			
			List<MaintenanceRequestDTO> list_of_all_services=maintenanceRequestDAO.search_all_services();
			session.setAttribute("search_services", list_of_all_services);
			
			request.setAttribute("error", "Reply to a service is e-Mailed");
			RequestDispatcher rd=request.getRequestDispatcher("admin/manageServices.jsp");
			rd.forward(request, response);
		}
	}
}
