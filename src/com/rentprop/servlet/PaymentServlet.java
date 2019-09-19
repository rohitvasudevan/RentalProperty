package com.rentprop.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rentprop.dao.ResidentDAO;
import com.rentprop.dto.PaymentDTO;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/paymentServlet")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String flag=request.getParameter("flag");
		if(flag.equals("search")){
			List<PaymentDTO> list_of_all_payments=ResidentDAO.findAllPayments();
			session.setAttribute("list_of_all_payments", list_of_all_payments);
			response.sendRedirect("admin/viewPaymentHistory.jsp");
		}
		else if(flag.equals("viewBill")){
			int resident_id=Integer.parseInt(request.getParameter("resident_id"));
			List<PaymentDTO> list_of_all_payment=ResidentDAO.findPendingBillByResidentId(resident_id);
			if(list_of_all_payment.size()>0){
			session.setAttribute("list_of_all_payment", list_of_all_payment);
			response.sendRedirect("user/viewPayment.jsp");
			}
			else{
				request.setAttribute("error", "You Don't have any pending Bills");
				RequestDispatcher rd=request.getRequestDispatcher("user/index.jsp");
				rd.forward(request, response);
			}
		}
		else if(flag.equals("viewPayments")){
			int resident_id=Integer.parseInt(request.getParameter("resident_id"));
			List<PaymentDTO> list_of_all_payment=ResidentDAO.findPaymentHistoryByResidentId(resident_id);
			if(list_of_all_payment.size()>0){
			session.setAttribute("list_of_all_payment", list_of_all_payment);
			response.sendRedirect("user/paymentHistory.jsp");
			}
			else{
				request.setAttribute("error", "You Don't have any payment History");
				RequestDispatcher rd=request.getRequestDispatcher("user/index.jsp");
				rd.forward(request, response);
			}
		}
		else if(flag.equals("success")){
			int payment_id=(Integer)session.getAttribute("payment_id");
			PaymentDTO paymentDTO=new PaymentDTO();
			paymentDTO.setPayment_id(payment_id);
			paymentDTO.setStatus("paid");
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			paymentDTO.setPayment_date(ts);
			
			ResidentDAO.update_payment(paymentDTO);
			request.setAttribute("error", "Thank You for Your Payment");
			RequestDispatcher rd=request.getRequestDispatcher("user/index.jsp");
			rd.forward(request, response);
		}
		else if(flag.equals("fail")){
			int payment_id=(Integer)session.getAttribute("payment_id");
			PaymentDTO paymentDTO=new PaymentDTO();
			paymentDTO.setPayment_id(payment_id);
			paymentDTO.setStatus("failed");
			
			ResidentDAO.update_payment(paymentDTO);
			request.setAttribute("error", "Sorry, your transaction failed");
			RequestDispatcher rd=request.getRequestDispatcher("user/index.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String flag=request.getParameter("flag");
		if(flag.equals("pay")){
			String firstname=request.getParameter("firstname");
			String lastname=request.getParameter("lastname");
			String emailId=request.getParameter("emailId");
			String phoneNo=request.getParameter("phoneNo");
			int payment_id=Integer.parseInt(request.getParameter("payment_id"));
			float rent=Float.parseFloat(request.getParameter("rent"));
			
			session.setAttribute("firstname1", firstname);
			session.setAttribute("lastname1", lastname);
			session.setAttribute("emailId1", emailId);
			session.setAttribute("phoneNo1", phoneNo);
			session.setAttribute("rent1", rent);
			session.setAttribute("surl", "http://localhost:8080/RentProp/user/success.jsp");
			session.setAttribute("furl", "http://localhost:8080/RentProp/user/fail.jsp");
			session.setAttribute("payment_id", payment_id);
			
			response.sendRedirect("user/payu.jsp");
			
		}
	}

}
