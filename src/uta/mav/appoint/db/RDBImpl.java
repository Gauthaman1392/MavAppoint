package uta.mav.appoint.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uta.mav.appoint.EncrDecr;
import uta.mav.appoint.PrimitiveTimeSlot;
import uta.mav.appoint.TimeSlotComponent;
import uta.mav.appoint.beans.Advisor;
import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.beans.CreateAdvisorBean;
import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.command.AddAppointmentType;
import uta.mav.appoint.db.command.AddTimeSlot;
import uta.mav.appoint.db.command.CheckTimeSlot;
import uta.mav.appoint.db.command.CheckUser;
import uta.mav.appoint.db.command.CreateAdvisor;
import uta.mav.appoint.db.command.CreateBugReport;
import uta.mav.appoint.db.command.CreateFeedback;
import uta.mav.appoint.db.command.CreateInitialAdvisorSettings;
import uta.mav.appoint.db.command.CreateStudentUser;
import uta.mav.appoint.db.command.DeleteAdvisor;
import uta.mav.appoint.db.command.DeleteTimeSlot;
import uta.mav.appoint.db.command.GetAdvisors;
import uta.mav.appoint.db.command.GetAllAdvisors;
import uta.mav.appoint.db.command.GetAllAppointments;
import uta.mav.appoint.db.command.GetAllStudentAppointments;
import uta.mav.appoint.db.command.GetAppointment;
import uta.mav.appoint.db.command.GetNotificationStatus;
import uta.mav.appoint.db.command.GetSetting;
import uta.mav.appoint.db.command.GetUser;
import uta.mav.appoint.db.command.GetUserID;
import uta.mav.appoint.db.command.SQLCmd;
import uta.mav.appoint.db.command.SearchAdvisor;
import uta.mav.appoint.db.command.UpdateAdvisor;
import uta.mav.appoint.db.command.UpdateAppointment;
import uta.mav.appoint.db.command.UpdateNotificationStatus;
import uta.mav.appoint.db.command.UpdateSetting;
import uta.mav.appoint.db.command.UpdateStudentPassword;
import uta.mav.appoint.db.command.UpdateUserProfile;
import uta.mav.appoint.flyweight.TimeSlotFlyweightFactory;
import uta.mav.appoint.helpers.TimeSlotHelpers;
import uta.mav.appoint.login.AdminUser;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.login.StudentUser;

public class RDBImpl implements DBImplInterface {

	public Connection connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String jdbcUrl = "jdbc:mysql://localhost:3306/mavappoint";
			String userid = "root";
			String password = "";
			Connection conn = DriverManager.getConnection(jdbcUrl, userid, password);
			return conn;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	// user login checking, check username and password against database
	// then return role if a match is found
	// using command pattern
	public LoginUser checkUser(GetSet set) throws SQLException {
		LoginUser user = null;
		try {
			set.setPassword(EncrDecr.encrypt(set.getPassword()));//EncrDecr
			set.setEmailAddress(set.getEmailAddress());
			SQLCmd cmd = new CheckUser(set.getEmailAddress(), set.getPassword());
			cmd.execute();
			user = (LoginUser) (cmd.getResult()).get(0);
			user.setEmail(user.getEmail());

		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

	public Boolean updateAppointment(Appointment a) {
		Boolean result = false;
		try {
			SQLCmd cmd = new UpdateAppointment(a);
			cmd.execute();
			result = (Boolean) (cmd.getResult()).get(0);
		} catch (Exception e) {

		}
		return result;
	}

	public int addUser(GetSet set) {
		int result = 0;
		try {
			set.setPassword(EncrDecr.encrypt(set.getPassword()));
			set.setEmailAddress(set.getEmailAddress());
			SQLCmd cmd = new CreateStudentUser(set);
			cmd.execute();
			result = (int) (cmd.getResult()).get(0);
		} catch (Exception sq) {
			System.out.printf(sq.toString());
		}
		return result;
	}

	// using command pattern
	public ArrayList<String> getAdvisors() throws SQLException {
		ArrayList<String> arraylist = new ArrayList<String>();
		try {
			SQLCmd cmd = new GetAdvisors();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i = 0; i < tmp.size(); i++) {
				arraylist.add(((String) tmp.get(i)));
			}
		} catch (Exception sq) {
			System.out.printf(sq.toString());
		}
		return arraylist;
	}

	public ArrayList<TimeSlotComponent> getAdvisorSchedule(String name) {
		ArrayList<TimeSlotComponent> array = new ArrayList<TimeSlotComponent>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String date =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Calendar cal = Calendar.getInstance();
			String time =  new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
			if (name.equals("all")) {
				String command = "SELECT pname,advising_date,advising_starttime,advising_endtime,id FROM user,advising_schedule,advisor_settings "
						+ "WHERE user.userid=advisor_settings.userid AND user.userid=advising_schedule.userid AND studentid is null AND advising_date >=? AND advising_endtime >=?";
				statement = conn.prepareStatement(command);
				statement.setString(1, date);
				statement.setString(2, time);
			} else {
				String command = "SELECT pname,advising_date,advising_starttime,advising_endtime,id FROM USER,ADVISING_SCHEDULE,ADVISOR_SETTINGS "
						+ "WHERE USER.userid=ADVISOR_SETTINGS.userid AND USER.userid=ADVISING_SCHEDULE.userid AND USER.userid=ADVISING_SCHEDULE.userid AND ADVISOR_SETTINGS.pname=? AND studentid is null  AND advising_starttime >=? AND advising_endtime >=?";
				statement = conn.prepareStatement(command);
				statement.setString(1, name);
				statement.setString(2, date);
				statement.setString(3, time);
			}
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				// Use flyweight factory to avoid build cost if possible
				PrimitiveTimeSlot set = (PrimitiveTimeSlot) TimeSlotFlyweightFactory.getInstance()
						.getFlyweight(res.getString(1) + "-" + res.getString(2), res.getString(3));
				set.setName(res.getString(1));
				set.setDate(res.getString(2));
				set.setStartTime(res.getString(3));
				set.setEndTime(res.getString(4));
				set.setUniqueId(res.getInt(5));
				array.add(set);
			}
			array = TimeSlotHelpers.createCompositeTimeSlot(array);
			conn.close();
		} catch (Exception e) {
			System.out.printf(e.toString());
		}
		return array;
	}

	public Boolean createAppointment(Appointment a, String email) {
		Boolean result = false;
		int student_id = 0;
		int advisor_id = 0;
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT userid from user where email=?";
			statement = conn.prepareStatement(command);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				student_id = rs.getInt(1);
			}
			command = "SELECT userid FROM advisor_settings WHERE advisor_settings.pname=?";
			statement = conn.prepareStatement(command);
			statement.setString(1, a.getPname());
			rs = statement.executeQuery();
			while (rs.next()) {
				advisor_id = rs.getInt(1);
			}
			// check for slots already taken
			command = "SELECT COUNT(*) FROM advising_schedule WHERE userid=? AND advising_date=? AND advising_starttime=? AND advising_endtime=? AND studentid is not null";
			statement = conn.prepareStatement(command);
			statement.setInt(1, advisor_id);
			statement.setString(2, a.getAdvisingDate());
			statement.setString(3, a.getAdvisingStartTime());
			statement.setString(4, a.getAdvisingEndTime());
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) < 1) {
					command = "INSERT INTO appointments (id,advisor_userid,student_userid,advising_date,advising_starttime,advising_endtime,appointment_type,studentid,description,student_email,student_phonenumber)"
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
					statement = conn.prepareStatement(command);
					statement.setInt(1, a.getAppointmentId());
					statement.setInt(2, advisor_id);
					statement.setInt(3, student_id);
					statement.setString(4, a.getAdvisingDate());
					statement.setString(5, a.getAdvisingStartTime());
					statement.setString(6, a.getAdvisingEndTime());
					statement.setString(7, a.getAppointmentType());
					statement.setInt(8, Integer.parseInt(a.getStudentid()));
					statement.setString(9, a.getDescription());
					statement.setString(10, email);
					statement.setString(11, a.getStudentPhoneNumber());
					statement.executeUpdate();
					command = "UPDATE advising_schedule SET studentid=? where userid=? AND advising_date=? and advising_starttime >= ? and advising_endtime <= ?";
					statement = conn.prepareStatement(command);
					statement.setInt(1, Integer.parseInt(a.getStudentid()));
					statement.setInt(2, advisor_id);
					statement.setString(3, a.getAdvisingDate());
					statement.setString(4, a.getAdvisingStartTime());
					statement.setString(5, a.getAdvisingEndTime());
					statement.executeUpdate();
					result = true;
				}
			}
			conn.close();
		} catch (Exception e) {
			System.out.printf(e.toString());
		}
		return result;
	}

	public ArrayList<Object> getAppointments(AdvisorUser user) {
		ArrayList<Object> appointments = new ArrayList<Object>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id,appointments.description,studentid,appointments.student_email FROM USER,APPOINTMENTS,ADVISOR_SETTINGS "
					+ "WHERE USER.email=? AND user.userid=appointments.advisor_userid AND advisor_settings.userid=appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());			
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				set.setDescription(rs.getString(8));
				set.setStudentid(rs.getString(9));
				set.setStudentEmail(rs.getString(10));
				appointments.add(set);
			}
			conn.close();
		} catch (Exception e) {
			System.out.printf(e.toString());
		}

		return appointments;
	}

	public ArrayList<Object> getAppointments(StudentUser user) {
		ArrayList<Object> appointments = new ArrayList<Object>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id,description,student_email FROM USER,APPOINTMENTS,ADVISOR_SETTINGS "
					+ "WHERE USER.email=? AND user.userid=appointments.student_userid AND advisor_settings.userid=appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				set.setDescription(rs.getString(8));
				set.setStudentid("Advisor only");
				set.setStudentEmail(rs.getString(9));
				appointments.add(set);
			}
			conn.close();
		} catch (Exception e) {
			System.out.printf(e.toString());
		}

		return appointments;
	}

	public ArrayList<Object> getAppointments(AdminUser user) {
		ArrayList<Object> appointments = new ArrayList<Object>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id FROM appointments INNER JOIN advisor_settings "
					+ "WHERE advisor_settings.userid = appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				appointments.add(set);
			}
			conn.close();
		} catch (Exception e) {
			System.out.printf(e.toString());
		}

		return appointments;
	}

	public Boolean cancelAppointment(int id) {
		Boolean result = false;
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT count(*),advising_date,advising_starttime, advising_endtime from appointments where id=?";
			statement = conn.prepareStatement(command);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					command = "DELETE FROM appointments where id=?";
					statement = conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.executeUpdate();
					command = "UPDATE advising_schedule SET studentid=null where advising_date=? AND advising_starttime >=? AND advising_endtime <=?";
					statement = conn.prepareStatement(command);
					statement.setString(1, rs.getString(2));
					statement.setString(2, rs.getString(3));
					statement.setString(3, rs.getString(4));
					statement.executeUpdate();
					result = true;
				}
			}
			conn.close();
		} catch (SQLException e) {
			System.out.printf("Error in Database: " + e.toString());
			return false;
		}
		return result;
	}

	public String addTimeSlot(AllocateTime at) {
		SQLCmd cmd = new GetUserID(at.getEmail());
		cmd.execute();
		int id = (int) cmd.getResult().get(0);
		cmd = new CheckTimeSlot(at, id);
		cmd.execute();
		if ((Boolean) cmd.getResult().get(0) == true) {
			cmd = new AddTimeSlot(at, id);
			cmd.execute();
			return (String) cmd.getResult().get(0);
		} else {
			return "Unable to add time slot.";
		}
	}

	public ArrayList<AppointmentType> getAppointmentTypes(String pname) {
		ArrayList<AppointmentType> ats = new ArrayList<AppointmentType>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT type,duration,user.email FROM  appointment_types,advisor_settings,user WHERE appointment_types.userid=advisor_settings.userid AND advisor_settings.userid=user.userid AND advisor_settings.pname=?";
			statement = conn.prepareStatement(command);
			statement.setString(1, pname);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				AppointmentType at = new AppointmentType();
				at.setType(rs.getString(1));
				at.setDuration(rs.getInt(2));
				at.setEmail(rs.getString(3));
				ats.add(at);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return ats;

	}

	public Boolean deleteTimeSlot(AllocateTime at) {
		Boolean b;
		SQLCmd cmd = new DeleteTimeSlot(at);
		cmd.execute();
		b = (Boolean) (cmd.getResult()).get(0);
		return b;
	}

	public Appointment getAppointment(String d, String t, String e) {
		Appointment app = null;
		try {
			SQLCmd cmd = new GetAppointment(d, t, e);
			cmd.execute();
			if (cmd.getResult().size() > 0) {
				app = (Appointment) (cmd.getResult()).get(0);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return app;
	}

	public Boolean createAdvisor(CreateAdvisorBean ca) {
		try {
			SQLCmd cmd = new CreateAdvisor(ca);
			cmd.execute();
			if ((Boolean) cmd.getResult().get(0)) {
				cmd = new GetUserID(ca.getEmail());
				cmd.execute();
				cmd = new CreateInitialAdvisorSettings((int) cmd.getResult().get(0), ca);
				cmd.execute();
				return (Boolean) cmd.getResult().get(0);
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public String addAppointmentType(AdvisorUser user, AppointmentType at) {
		String msg = null;
		SQLCmd cmd = new GetUserID(user.getEmail());
		cmd.execute();
		cmd = new AddAppointmentType(at, (int) cmd.getResult().get(0));
		cmd.execute();
		return (String) cmd.getResult().get(0);
	}

	// feedback requirement
	public String createFeedback(String feedback) {
		String msg = null;
		SQLCmd cmd = new CreateFeedback(feedback);
		cmd.execute();
		return (String) cmd.getResult().get(0);
	}

	public String createBugReport(String bugreport) {
		String msg = null;
		SQLCmd cmd = new CreateBugReport(bugreport);
		cmd.execute();
		return (String) cmd.getResult().get(0);
	}
	
	public int updateStudentPassword(String email, String currentPassword, String newPassword, String validated) {
		String msg = null;
		SQLCmd cmd = null;
		try {
			cmd = new UpdateStudentPassword(email, EncrDecr.encrypt(currentPassword), EncrDecr.encrypt(newPassword), validated);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();
		return (int)cmd.getResult().get(0);
	}
	
	public GetSet getUser(String email)
	{
		SQLCmd cmd = new GetUser(email);
		cmd.execute();
		GetSet set = new GetSet();
		set.setStudentId((int)cmd.getResult().get(0));
		set.setName((String)cmd.getResult().get(1));
		try {
			set.setEmailAddress((String)cmd.getResult().get(2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}
	
	public boolean updateUserProfile(String name, String email, int id)
	{
		SQLCmd cmd = null;
		try {
			cmd = new UpdateUserProfile(name, email, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();		
		return (boolean)cmd.getResult().get(0);
	}
	
	public String getNotificationStatus(String email)
	{
		SQLCmd cmd = null;
		try {
			cmd = new GetNotificationStatus(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();		
		return (String)cmd.getResult().get(0);
	}
	
	public boolean updateNotificationStatus(String email, String notificationStatus){
		SQLCmd cmd = null;
		try {
			cmd = new UpdateNotificationStatus(email, notificationStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();		
		return (boolean)cmd.getResult().get(0);
	}
	
	public ArrayList<Advisor> getAllAdvisors() {
		ArrayList<Advisor> advisors = new ArrayList<Advisor>();
		try {
			SQLCmd cmd = new GetAllAdvisors();
			cmd.execute();
			if (cmd.getResult().size() > 0) {
				advisors = (ArrayList<Advisor>) (cmd.getResult()).get(0);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return advisors;
	}
	
	public boolean updateAdvisor(int originalId, int id, String name, String email, String role){
		SQLCmd cmd = null;
		try {
			cmd = new UpdateAdvisor(originalId, id, name, email, role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();		
		return (boolean)cmd.getResult().get(0);
	}
	
	public boolean deleteAdvisor(int id) throws SQLException
	{
		SQLCmd cmd = null;
		try {
			cmd = new DeleteAdvisor(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();		
		return (boolean)cmd.getResult().get(0);
	}
	
	public ArrayList<Appointment> getAllAppointments(String todayDate, String tomorrowDate){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try {
			SQLCmd cmd = new GetAllAppointments(todayDate, tomorrowDate);
			cmd.execute();
			if (cmd.getResult().size() > 0) {
				appointments = (ArrayList<Appointment>) (cmd.getResult()).get(0);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return appointments;
	}
	
	public ArrayList<Appointment> getAllStudentAppointments(String userId){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try {
			SQLCmd cmd = new GetAllStudentAppointments(userId);
			cmd.execute();
			if (cmd.getResult().size() > 0) {
				appointments = (ArrayList<Appointment>) (cmd.getResult()).get(0);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return appointments;
	}
	
	public Advisor searchAdvisor(String query) throws SQLException{
		Advisor advisor = new Advisor();
		try {
			SQLCmd cmd = new SearchAdvisor(query);
			cmd.execute();
			if (cmd.getResult().size() > 0) {
				advisor = (Advisor) (cmd.getResult()).get(0);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return advisor;
	}
	
	public String getSetting(String setting) throws SQLException{
		SQLCmd cmd = null;
		try {
			cmd = new GetSetting(setting);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();		
		return (String)cmd.getResult().get(0);
	}
	
	public boolean updateSetting(String setting, String value) throws SQLException{
		SQLCmd cmd = null;
		try {
			cmd = new UpdateSetting(setting,value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmd.execute();		
		return (boolean)cmd.getResult().get(0);
	}
}
