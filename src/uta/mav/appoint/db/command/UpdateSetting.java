package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

import uta.mav.appoint.beans.Appointment;

public class UpdateSetting extends SQLCmd {
	String setting;
	String value;
	Boolean b = false;

	public UpdateSetting(String setting, String value) {
		this.setting = setting;
		this.value = value;
	}

	public void queryDB() {
		try {
			String command = "UPDATE settings SET value=? WHERE name=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, value);
			statement.setString(2, setting);
			statement.executeUpdate();
			b = true;
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void processResult() {
		result.add(b);
	}

}
