package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

import uta.mav.appoint.beans.CreateAdvisorBean;

public class CreateAdvisor extends SQLCmd{
	String id;
	String email;
	String password;
	String role;
	String pname;
	Boolean b;
	
	public CreateAdvisor(CreateAdvisorBean ca){
		email = ca.getEmail();
		password = ca.getPassword();
		pname = ca.getPname();
		id = ca.getId();
		role = ca.getRole();
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "INSERT INTO user (userID,name,email,password,role,validated)"
							+" values(?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,id);
			statement.setString(2,pname);
			statement.setString(3,email);
			statement.setString(4,password);
			statement.setString(5,role);
			statement.setInt(6,1);
			statement.executeUpdate();
			b = true;
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
