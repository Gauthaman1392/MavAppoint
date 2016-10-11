package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.GetSet;

public class DeleteAdvisor extends SQLCmd{
	int id;
	Boolean b = false;
	
	public DeleteAdvisor(int id){		
		this.id = id;
	}
	
	public void queryDB(){
		try{
			String command = "DELETE FROM USER WHERE USERID=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1, id);			
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
