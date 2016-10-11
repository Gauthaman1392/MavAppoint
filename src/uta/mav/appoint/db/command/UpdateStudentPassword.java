package uta.mav.appoint.db.command;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.beans.GetSet;

public class UpdateStudentPassword extends SQLCmd {
	String email;
	String currentPassword;
	String newPassword;
	String validated;

	int resu;

	public UpdateStudentPassword(String email, String currentPassword, String newPassword, String validated) {
		this.email = email;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.validated = validated;
		resu = 0;
	}

	@Override
	public void queryDB() {
		try {
			String command = "SELECT PASSWORD FROM user WHERE user.EMAIL = '"+email+"'";			
			PreparedStatement statement = conn.prepareStatement(command);		
			res = statement.executeQuery();
			while (res.next()) {
				String test = res.getString(1);
				if(!(res.getString(1).equals(currentPassword))){
					resu = 0;
					return;
				}
				else{
				 command = "UPDATE user SET PASSWORD = ?, LASTUPDATED = ?, VALIDATED=? WHERE user.EMAIL = ?";					
				 statement = conn.prepareStatement(command);
				 statement.setString(1, newPassword);
				 Calendar calendar = Calendar.getInstance();
				 java.sql.Timestamp timeStamp = new java.sql.Timestamp(calendar.getTime().getTime());	
				 statement.setTimestamp(2, timeStamp);
				 statement.setString(3, validated);
				 statement.setString(4, email);
				 statement.executeUpdate();
				}
			}
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
