package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.beans.Advisor;

public class SearchAdvisor extends SQLCmd{
	
	String query;
	
	public SearchAdvisor(String query){
		super();
		this.query = query;
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT * FROM USER WHERE ROLE=? AND USER.EMAIL = ? OR USER.NAME = ? LIMIT 1";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,"advisor");
			statement.setString(2,query);
			statement.setString(3,query);
			res = statement.executeQuery();	
		}
		catch(SQLException sq){
			System.out.printf(sq.toString());
		}
	}
	
	@Override
	public void processResult(){
		Advisor advisor = new Advisor();
		try{
			while (res.next()){
				advisor.setId(res.getString(1));
				advisor.setName(res.getString(2));
				advisor.setEmail(res.getString(3));
				advisor.setRole(res.getString(5));
				result.add(advisor);
			}
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
