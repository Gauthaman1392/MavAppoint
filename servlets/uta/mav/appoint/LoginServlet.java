package uta.mav.appoint;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		String emailAddress = request.getParameter("emailAddress");
		String password = request.getParameter("password");

		GetSet sets = new GetSet();
		sets.setEmailAddress(emailAddress);
		sets.setPassword(password);
		try {
			// call db manager and authenticate user, return value will be 0 or
			// an integer indicating a role
			DatabaseManager dbm = new DatabaseManager();
			LoginUser user = dbm.checkUser(sets);
			if (user != null) {
				session.setAttribute("user", user);
				if (user.getHeader().equals("student_header")) {
					Calendar now = Calendar.getInstance();
					long currentTime = now.getTimeInMillis();
					long lastUpdatedTime = user.getPasswordLastUpdated().getTime();
					if (user.getValidated().equals("0") && (currentTime - lastUpdatedTime) / (60 * 60 * 1000) >= Long
							.parseLong(dbm.getSetting("passwordexpiry"))) {
						String newPassword = generateRandomPassword();
						dbm.updateStudentPassword(emailAddress, password, newPassword, "0");
						NotifyRegistration.sendRegistrationDetails(emailAddress, newPassword,
								dbm.getSetting("passwordexpiry"));
						response.setHeader("Refresh", "2; URL=login");
						request.getRequestDispatcher("/WEB-INF/jsp/views/loginfailure.jsp").forward(request, response);
						return;
					}
					int year = now.get(Calendar.YEAR);
					Timestamp currentYearTimestamp = Timestamp.valueOf(year + "-07-01 12:00:00.0");
					long diffInDays = (currentYearTimestamp.getTime() - user.getPasswordLastUpdated().getTime())
							/ (24 * 60 * 60 * 1000);
					if (diffInDays >= 365 || user.getValidated().equals("0")) {
						response.sendRedirect("updatepassword");
					} else {
						response.sendRedirect("index");
					}

				} else {
					response.sendRedirect("index");
				}
			} else {
				// redirect back to login if authentication fails
				// need to add a "invalid username or password" response
				response.sendRedirect("login");
			}
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("login");
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
