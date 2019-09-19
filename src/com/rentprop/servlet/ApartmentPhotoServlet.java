package com.rentprop.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;

import com.rentprop.dao.ApartmentDAO;
import com.rentprop.dao.ApartmentPhotoDAO;
import com.rentprop.dto.ApartmentDTO;
import com.rentprop.dto.ApartmentPhotoDTO;

/**
 * Servlet implementation class apartmentPhotoServlet
 */
@WebServlet("/apartmentPhotoServlet")
@MultipartConfig
public class ApartmentPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");
		if (flag.equals("dropdown")) {
			ApartmentDAO ApartmentDAO = new ApartmentDAO();
			List<ApartmentDTO> list_of_all_apartments = ApartmentDAO.searchAllApartments();
			session.setAttribute("search_apartments_address", list_of_all_apartments);
			response.sendRedirect("admin/addApartmentPhotos.jsp");
		}

		else if (flag.equals("search")) {
			ApartmentPhotoDAO ApartmentPhotoDAO = new ApartmentPhotoDAO();
			List<ApartmentPhotoDTO> list_of_all_apartments_photos = ApartmentPhotoDAO.search_all_apartments_photos();
			session.setAttribute("search_apartments_photos", list_of_all_apartments_photos);
			response.sendRedirect("admin/manageApartmentPhotos.jsp");

		} else if (flag.equals("delete")) {

			int photo_id = Integer.parseInt(request.getParameter("photo_id"));
			ApartmentPhotoDTO ApartmentPhotoDTO = new ApartmentPhotoDTO();
			ApartmentPhotoDTO.setPhotoId(photo_id);
			ApartmentPhotoDAO ApartmentPhotoDAO = new ApartmentPhotoDAO();
			ApartmentPhotoDAO.delete_apartment_photos(ApartmentPhotoDTO);

			List<ApartmentPhotoDTO> list_of_all_apartments_photos = ApartmentPhotoDAO.search_all_apartments_photos();
			session.setAttribute("search_apartments_photos", list_of_all_apartments_photos);
			request.setAttribute("error", "Apartment Photo Deleted");
			RequestDispatcher rd = request.getRequestDispatcher("admin/manageApartmentPhotos.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean fileTooLarge = false;
		HttpSession session = request.getSession();
		String flag = request.getParameter("flag");
		if (flag.equals("add")) {
			byte[] buffer = null;
			for (Part filePart : request.getParts()) {
				if (filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().equals("")) {
					if (filePart.getSize() > (1024 * (2 * 1024))) {
						fileTooLarge = true;

						request.setAttribute("errorMsg", "Photo of size more than 2 MB are not allowed. Photo could not be saved.");
					} else {
					
						String fileName = filePart.getSubmittedFileName();
						session.setAttribute("fileName", fileName);
						InputStream fileContent = filePart.getInputStream();
						buffer = new byte[fileContent.available()];
						fileContent.read(buffer);
						String encryptFileName = encryptFileName(fileName);
						session.setAttribute("encryptedName", encryptFileName);
					}
				}
			}
			if (! fileTooLarge) {
				String file_name = (String) session.getAttribute("fileName");
				String encrypted_name = (String) session.getAttribute("encryptedName");
				int apartment_id = Integer.parseInt(request.getParameter("apartmentID"));
				System.out.println("Apartment id " + apartment_id);
				ApartmentPhotoDTO apartmentPhotoDTO = new ApartmentPhotoDTO();
				apartmentPhotoDTO.setFileName(file_name);
				apartmentPhotoDTO.setEncryptedName(encrypted_name);
				
				apartmentPhotoDTO.setPhoto(buffer);
				ApartmentDTO ApartmentDTO = new ApartmentDTO();
				ApartmentDTO.setApartmentId(apartment_id);
				apartmentPhotoDTO.setApartmentDTO(ApartmentDTO);
	
				ApartmentPhotoDAO ApartmentPhotoDAO = new ApartmentPhotoDAO();
				ApartmentPhotoDAO.add_photos(apartmentPhotoDTO);
				request.setAttribute("error", "Apartment Photo added");
			}
			// values for dropdown menu
			ApartmentDAO ApartmentDAO = new ApartmentDAO();
			List<ApartmentDTO> list_of_all_apartments = ApartmentDAO.searchAllApartments();
			session.setAttribute("search_apartments_address", list_of_all_apartments);

			RequestDispatcher rd = request.getRequestDispatcher("admin/addApartmentPhotos.jsp");
			rd.forward(request, response);
		}
	}

	private String encryptFileName(String fileName) {

		Random r = new Random();
		String file[] = fileName.split("\\.");

		byte[] unencodedFile = file[0].getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
		}
		md.reset();
		md.update(unencodedFile);
		byte[] encodedFile = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedFile.length; i++) {
			if (((int) encodedFile[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) encodedFile[i] & 0xff, 16));
		}

		String encryptedFileName = (buf.toString()).concat(String.valueOf(r.nextInt()));
		return encryptedFileName + "." + file[1];

	}
}
