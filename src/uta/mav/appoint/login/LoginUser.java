package uta.mav.appoint.login;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.sun.jna.platform.win32.Sspi.TimeStamp;

import uta.mav.appoint.visitor.Visitor;

public class LoginUser {
	String name;
	String email;
	String msg;
	String validated;
	int id;
	Timestamp passwordLastUpdated;
	
	public LoginUser(){
		name="";
		email = "";
		msg = "";		
	}
	
	public void accept(Visitor v){
		v.check(this);
	}
	
	public String getPname(){
		return "";
	}
	
	public ArrayList<Object> accept(Visitor v, Object o, Object t){//allow javabean to be passed in
		return v.check(this,o,t);
	}
		
	
	public LoginUser(String em, String name) {
		email = em;
		this.name = name;
	}
	
	/*
	 * @return the header - override in extended classes for proper header display
	 */
	public String getHeader(){
		return "header";
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Timestamp getPasswordLastUpdated() {
		return passwordLastUpdated;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setMsg(String s){
		this.msg = s;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getValidated(){
		return this.validated;
	}
	
	public void setValidated(String validated){
		this.validated = validated;
	}
}
