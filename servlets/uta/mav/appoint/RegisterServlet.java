package uta.mav.appoint;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.DatabaseManager;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int userId;
	String name;
	String email;
	String password;
	String rpassword;
	HttpSession session;
	String role;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();

		userId = Integer.parseInt(request.getParameter("userId").trim());
		name = request.getParameter("name");
		email = request.getParameter("emailAddress");
		password = request.getParameter("password");
		rpassword = request.getParameter("repeatPassword");
		role = "student";
		// need to add check for maverick email address
		// need to add check that both passwords match
		// need to redirect back to register with correct error message
		GetSet set = new GetSet();
		set.setStudentId(userId);
		set.setName(name);
		set.setEmailAddress(email);
		password = generateRandomPassword();
		set.setPassword(password);
		set.setRole(role);
		try {
			DatabaseManager dbm = new DatabaseManager();
			int check = dbm.addUser(set);
			if (check == 1) {
				// if adduser successful, log in as added user and redirect
				// back to start
				session.setAttribute("role", "student");
				session.setAttribute("name", name);
				session.setAttribute("emailAddress", email);
				response.sendRedirect("login");
				NotifyRegistration.sendRegistrationDetails(email, password, dbm.getSetting("passwordexpiry"));
			} else {
				// if unable to log in, add error message and redirect back to
				// register
				request.setAttribute("error", "Unable to add user");
				request.getRequestDispatcher("register").forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static String generateRandomPassword() {
		Random random = new SecureRandom();
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

		String password = "";
		for (int i = 0; i < 8; i++) {
			int index = (int) (random.nextDouble() * letters.length());
			password += letters.substring(index, index + 1);
		}
		return password;
	}
}
