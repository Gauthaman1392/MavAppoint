package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.GetSet;

public class UpdateAdvisor extends SQLCmd{
	int originalId;
	int id;
	String name;
	String email;
	String role;
	Boolean b = false;
	
	public UpdateAdvisor(int originalId, int id, String name, String email, String role){
		this.name = name;
		this.email = email;
		this.id = id;
		this.originalId = originalId;
		this.role = role;
	}
	
	public void queryDB(){
		try{
			String command = "UPDATE USER SET USERID=?,NAME=?,EMAIL=?,ROLE=? where USERID=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1, id);
			statement.setString(2, name);
			statement.setString(3, email);
			statement.setString(4, role);
			statement.setInt(5, originalId);
			statement.executeUpdate();
			b=true;
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
	}
	
	public void processResult(){
		result.add(b);
	}

}
