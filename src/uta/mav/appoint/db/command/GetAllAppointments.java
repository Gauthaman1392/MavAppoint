package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import uta.mav.appoint.beans.Advisor;
import uta.mav.appoint.beans.Appointment;

public class GetAllAppointments extends SQLCmd{
	String todayDate;
	String tomorrowDate;
	
	public GetAllAppointments(String todayDate, String tomorrowDate){
		this.todayDate= todayDate;
		this.tomorrowDate = tomorrowDate;
	}
	
	public void queryDB(){
		try{
		String command = "SELECT advising_date,advising_starttime,advising_endtime,appointment_type,student_email FROM appointments a,user u WHERE a.student_userid=u.userid AND (advising_date =? OR advising_date =?) ORDER BY advising_date,advising_starttime";
		PreparedStatement statement = conn.prepareStatement(command);
		statement.setString(1,todayDate);
		statement.setString(2,tomorrowDate);
		res = statement.executeQuery();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public void processResult(){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try{
			while (res.next()){
				Appointment app = new Appointment();
				app.setAdvisingDate(res.getString(1));
				app.setAdvisingStartTime(res.getString(2));
				app.setAdvisingEndTime(res.getString(3));
				app.setAppointmentType(res.getString(4));
				app.setStudentEmail(res.getString(5));
				appointments.add(app);
			}
			result.add(appointments);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
