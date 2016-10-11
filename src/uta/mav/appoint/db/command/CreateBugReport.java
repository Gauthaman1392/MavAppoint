package uta.mav.appoint.db.command;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;

import uta.mav.appoint.beans.AppointmentType;

public class CreateBugReport extends SQLCmd {
	String bugreport;
	String resu;
	
	public CreateBugReport(String bugreport){
		this.bugreport = bugreport;
		resu = "";
	}
	
	@Override
	public void queryDB(){
		try{
			String command = "INSERT INTO bugreport (bugreport, dateTime)"
					+ " values(?,?)";
			PreparedStatement statement = conn.prepareStatement(command); 
			statement.setString(1,bugreport);
			Calendar calendar = Calendar.getInstance();
		    java.sql.Timestamp timeStamp = new java.sql.Timestamp(calendar.getTime().getTime());		     
			statement.setTimestamp(2,timeStamp);
			statement.executeUpdate();
			resu = "Bug report created.";
			}
		catch (Exception e){
			System.out.println(e);	
		}
		
	}
	
	@Override
	public void processResult(){
		result.add(resu);
		
	}	
}
