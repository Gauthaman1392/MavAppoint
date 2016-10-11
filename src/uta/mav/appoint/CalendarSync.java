package uta.mav.appoint;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.rmi.server.UID;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;

public class CalendarSync {

	public static String generateICS(String userId) {
		DatabaseManager dbm = new DatabaseManager();
		StringBuffer sb = new StringBuffer();
		try {
			ArrayList<Appointment> appointments = dbm.getAllStudentAppointments(userId);
			StringBuffer buffer = sb.append("BEGIN:VCALENDAR\r\n"
					+ "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\r\n" + "VERSION:2.0\r\n"
					+ "METHOD:PUBLISH\r\n" + "BEGIN:VTIMEZONE\r\n" + "TZID:Central Standard Time\r\n"
					+ "BEGIN:STANDARD\r\n" + "DTSTART:16011104T020000\r\n"
					+ "RRULE:FREQ=YEARLY;BYDAY=1SU;BYMONTH=11\r\n" + "TZOFFSETFROM:-0500\r\n" + "TZOFFSETTO:-0600\r\n"
					+ "END:STANDARD\r\n" + "BEGIN:DAYLIGHT\r\n" + "DTSTART:16010311T020000\r\n"
					+ "RRULE:FREQ=YEARLY;BYDAY=2SU;BYMONTH=3\r\n" + "TZOFFSETFROM:-0600\r\n" + "TZOFFSETTO:-0500\r\n"
					+ "END:DAYLIGHT\r\n" + "END:VTIMEZONE\r\n");

			for (Appointment appoint : appointments) {
				UID uid = new UID();

				String startTime = new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(appoint.getAdvisingDate() + " " + appoint.getAdvisingStartTime()));
				String endTime = new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(appoint.getAdvisingDate() + " " + appoint.getAdvisingEndTime()));
				sb.append("BEGIN:VEVENT\r\n" + "DTSTART;TZID=Central Standard Time:" + startTime + "00\r\n"
						+ "DTEND;TZID=Central Standard Time:" + endTime + "00\r\n" + "LOCATION:Advisor Office\r\n"
						+ "TRANSP:OPAQUE\r\n" + "SEQUENCE:0\r\n" + "UID:" + uid + "\r\n" + "DTSTAMP:20141118T120102\r\n"
						+ "CATEGORIES:Meeting\r\n" + "SUMMARY:Appointment with advisor\r\n" + "PRIORITY:1\r\n"
						+ "CLASS:PUBLIC\r\n" + "BEGIN:VALARM\r\n" + "TRIGGER:PT1440M\r\n" + "ACTION:DISPLAY\r\n"
						+ "DESCRIPTION:Reminder\r\n" + "END:VALARM\r\n" + "END:VEVENT\r\n");
			}
			sb.append("END:VCALENDAR");
			System.out.println(sb);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
