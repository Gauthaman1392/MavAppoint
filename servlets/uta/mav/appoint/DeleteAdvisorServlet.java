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
public class DeleteAdvisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int id;	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		id = Integer.parseInt(request.getParameter("id").trim());		
		try {
			DatabaseManager dbm = new DatabaseManager();
			boolean done = dbm.deleteAdvisor(id);
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
