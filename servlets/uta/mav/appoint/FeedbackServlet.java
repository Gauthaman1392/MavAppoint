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
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AddAppointmentTypeVisitor;
import uta.mav.appoint.visitor.Visitor;

/**
 * Servlet implementation class FeedbackServlet
 */
public class FeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeedbackServlet() {
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
		} catch (Exception e) {

		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/feedback.jsp").forward(request, response);
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
			String feedback = request.getParameter("feedbackField");
			DatabaseManager db = new DatabaseManager();
			db.createFeedback(feedback);

			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write("Feedback has been submitted.");
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.printf(e.toString());
		}
	}
}
