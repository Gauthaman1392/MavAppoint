package uta.mav.appoint.db.command;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;

import uta.mav.appoint.beans.AppointmentType;

public class CreateFeedback extends SQLCmd {
	String feedback;
	int duration;
	String resu;
	
	public CreateFeedback(String feedback){
		this.feedback = feedback;
		resu = "";
	}
	
	@Override
	public void queryDB(){
		try{
			String command = "INSERT INTO feedback (feedback, dateTime)"
					+ " values(?,?)";
			PreparedStatement statement = conn.prepareStatement(command); 
			statement.setString(1,feedback);
			Calendar calendar = Calendar.getInstance();
		    java.sql.Timestamp timeStamp = new java.sql.Timestamp(calendar.getTime().getTime());		     
			statement.setTimestamp(2,timeStamp);
			statement.executeUpdate();
			resu = "Feedback created.";
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
