package uta.mav.appoint.db.command;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.beans.GetSet;

public class CreateStudentUser extends SQLCmd {
	int userId;
	String name;
	String email;
	String password;
	String role;

	int resu;

	public CreateStudentUser(GetSet get) {
		this.userId = get.getStudentId();
		this.name = get.getName();
		this.email = get.getEmailAddress();
		this.password = get.getPassword();
		this.role = get.getRole(); 
		resu = 0;
	}

	@Override
	public void queryDB() {
		try {
			String command = "INSERT INTO user(USERID, NAME, EMAIL, PASSWORD, ROLE, VALIDATED, LASTUPDATED)" + " values(?,?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1, userId);
			statement.setString(2, name);
			statement.setString(3, email);
			statement.setString(4, password);
			statement.setString(5, role);
			statement.setInt(6, 0);
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp timeStamp = new java.sql.Timestamp(calendar.getTime().getTime());
			statement.setTimestamp(7, timeStamp);
			statement.executeUpdate();
			resu = 1;
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void processResult() {
		result.add(resu);

	}
}
