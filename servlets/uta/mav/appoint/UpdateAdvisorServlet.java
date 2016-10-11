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
public class UpdateAdvisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String email;
	int id;
	String name;
	String role;
	int originalId;
	HttpSession session;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");
		originalId = Integer.parseInt(request.getParameter("originalId").trim());
		id = Integer.parseInt(request.getParameter("advisorId").trim());
		name = request.getParameter("name");
		email = request.getParameter("email");
		role = request.getParameter("role");
		try {
			DatabaseManager dbm = new DatabaseManager();
			boolean done = dbm.updateAdvisor(originalId, id, name, email, role);
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if (done) {
				out.write("done");
			} else {
				out.write("failed");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
