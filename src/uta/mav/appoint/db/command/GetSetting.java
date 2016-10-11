package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

import uta.mav.appoint.beans.Appointment;

public class GetSetting extends SQLCmd{
	String setting;
	String email;
	String time;
	
	public GetSetting(String setting){
		this.setting = setting;
	}
	
	public void queryDB(){
		try{
		String command = "SELECT value FROM settings WHERE name=?";
		PreparedStatement statement = conn.prepareStatement(command);
		statement.setString(1,setting);
		res = statement.executeQuery();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public void processResult(){
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
