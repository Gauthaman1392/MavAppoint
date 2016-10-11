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
public class UpdateNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String email;
	String notificationStatus;
	HttpSession session;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");
		notificationStatus = request.getParameter("notificationstatus");

		try {
			DatabaseManager dbm = new DatabaseManager();
			boolean done = dbm.updateNotificationStatus(user.getEmail(), notificationStatus);
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if (done) {
				out.write("Notification status changed successfully.");
			} else {
				out.write("There was a problem in changing notification status.");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
