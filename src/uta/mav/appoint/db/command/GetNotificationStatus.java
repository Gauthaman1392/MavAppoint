package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

public class GetNotificationStatus extends SQLCmd{

	String email;
	
	public GetNotificationStatus(String e){
		email = e;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "SELECT NOTIFICATION FROM advisor_settings WHERE EMAIL=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,email);
			res = statement.executeQuery();
			}
			catch(Exception e){
				System.out.println(e);
			}
	}

	@Override
	public void processResult() {
		try{
			while (res.next()){
				result.add(res.getString(1));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
