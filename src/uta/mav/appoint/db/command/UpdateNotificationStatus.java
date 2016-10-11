package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

public class UpdateNotificationStatus extends SQLCmd{

	String email;
	String notificationStatus;
	Boolean b = false;
	
	public UpdateNotificationStatus(String e, String notificationStatus){
		email = e;
		this.notificationStatus = notificationStatus;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "UPDATE advisor_settings SET NOTIFICATION=? WHERE EMAIL=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, notificationStatus);
			statement.setString(2,email);
			statement.executeUpdate();
			b=true;
			}
			catch(Exception e){
				System.out.println(e);
			}
	}

	@Override
	public void processResult() {
		result.add(b);
	}
}
