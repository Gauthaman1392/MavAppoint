package uta.mav.appoint.login;

import java.sql.Timestamp;
import java.util.ArrayList;

import uta.mav.appoint.visitor.Visitor;

public class StudentUser extends LoginUser{

	Timestamp passwordLastUpdated;
	int id;
	String validated;

	public StudentUser(String em, String name, int id, Timestamp t, String validated){
		super(em, name);
		passwordLastUpdated = t;
		this.id=id;
		this.validated = validated;
	}

	@Override
	public String getValidated(){
		return this.validated;
	}
	
	@Override
	public void setValidated(String validated){
		this.validated = validated;
	}
	
	@Override
	public String getHeader(){
		return "student_header";
	}
	
	@Override
	public Timestamp getPasswordLastUpdated(){
		return passwordLastUpdated;
	}
	
	@Override
	public int getId(){
		return this.id;
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
