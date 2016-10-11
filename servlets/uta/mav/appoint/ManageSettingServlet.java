package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AddAppointmentTypeVisitor;
import uta.mav.appoint.visitor.Visitor;

/**
 * Servlet implementation class FeedbackServlet
 */
@WebServlet("/manage_settings")
public class ManageSettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageSettingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");
		if (user == null) {
			user = new LoginUser();
			session.setAttribute("user", user);
		}
		try {
			header = "templates/" + user.getHeader() + ".jsp";
			DatabaseManager db = new DatabaseManager();
			String expiry = db.getSetting("passwordexpiry");
			
			request.setAttribute("expiry", expiry);
		} catch (Exception e) {

		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/manage_settings.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");
		if (user == null) {
			user = new LoginUser();
			session.setAttribute("user", user);
		}
		try {
			String expiry = request.getParameter("expiry").replaceAll("[^0-9]", "");
			DatabaseManager db = new DatabaseManager();
			boolean result = db.updateSetting("passwordexpiry", expiry);
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if (result) {
				out.write("Settings updated successfully.");
			} else {
				out.write("Failed to update settings.");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.printf(e.toString());
		}
	}
}
