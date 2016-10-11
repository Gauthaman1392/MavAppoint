package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.GetSet;

public class UpdateUserProfile extends SQLCmd{
	String name;
	String email;
	int id;
	Boolean b = false;
	
	public UpdateUserProfile(String name, String email, int id){
		this.name = name;
		this.email = email;
		this.id = id;
	}
	
	public void queryDB(){
		try{
			String command = "UPDATE USER SET NAME=?,EMAIL=? where USERID=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, name);
			statement.setString(2, email);
			statement.setInt(3, id);
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
