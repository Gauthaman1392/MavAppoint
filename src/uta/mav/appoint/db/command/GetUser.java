package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

public class GetUser extends SQLCmd{

	String email;
	
	public GetUser(String e){
		email = e;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "SELECT userid, NAME, EMAIL FROM user WHERE email=?";
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
				result.add(res.getInt(1));
				result.add(res.getString(2));
				result.add(res.getString(3));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
