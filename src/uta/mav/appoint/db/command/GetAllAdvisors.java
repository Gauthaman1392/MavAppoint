package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.beans.Advisor;
import uta.mav.appoint.beans.Appointment;

public class GetAllAdvisors extends SQLCmd{
	
	public GetAllAdvisors(){
		super();
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT USERID,NAME,EMAIL,ROLE FROM USER WHERE ROLE=? OR ROLE=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,"advisor");
			statement.setString(2,"lead_advisor");
			res = statement.executeQuery();	
		}
		catch(SQLException sq){
			System.out.printf(sq.toString());
		}
	}
	
	@Override
	public void processResult(){
		ArrayList<Advisor> advisors = new ArrayList<Advisor>();	
		try{
			while (res.next()){
				Advisor advisor = new Advisor();
				advisor.setId(res.getString(1));
				advisor.setName(res.getString(2));
				advisor.setEmail(res.getString(3));
				advisor.setRole(res.getString(4));
				advisors.add(advisor);				
			}
			result.add(advisors);
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
