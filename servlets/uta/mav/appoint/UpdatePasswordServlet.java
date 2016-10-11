package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class RegisterServlet
 */
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int userId;
	String currentPassword;
	String newPassword;
	HttpSession session;
	String role;
	private String header;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");
		if (user != null) {
			try {
				header = "templates/" + user.getHeader() + ".jsp";
			} catch (Exception e) {
				System.out.println("UpdatePassword error : " + e);
			}
		} else {
			if (user == null) {
				user = new LoginUser();
				session.setAttribute("user", user);
			}
			header = "templates/header.jsp";
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/updatepassword.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");
		currentPassword = request.getParameter("currentPassword");
		newPassword = request.getParameter("newPassword");

		try {
			header = "templates/" + user.getHeader() + ".jsp";
			DatabaseManager dbm = new DatabaseManager();
			int check = dbm.updateStudentPassword(user.getEmail(), currentPassword, newPassword, "1");
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if (check == 1) {
				out.write("1");
			} else {
				out.write("0");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
