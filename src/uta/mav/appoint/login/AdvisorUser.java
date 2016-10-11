package uta.mav.appoint.login;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.sun.jna.platform.win32.Sspi.TimeStamp;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.visitor.Visitor;

public class AdvisorUser extends LoginUser{
	String pname;
	
	public AdvisorUser(String em, String name, String p){
		super(em, name);
		pname = p;
	}
	
	@Override
	public String getHeader(){
		return "advisor_header";
	}

	/**
	 * @return the pname
	 */
	@Override
	public String getPname() {
		return pname;
	}
	
	@Override
	public void accept(Visitor v){
		v.check(this);
	}
	
	@Override
	public ArrayList<Object> accept(Visitor v, Object o, Object t){//allow javabean to be passed in
		return v.check(this,o,t);
	}
}
