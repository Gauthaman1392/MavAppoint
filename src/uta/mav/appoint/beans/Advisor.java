package uta.mav.appoint.beans;

import java.io.Serializable;

public class Advisor implements Serializable{

	/**
	 * JavaBean for Appointments db table
	 */
	private static final long serialVersionUID = -3734663824525723817L;
	String id;
	String name;
	String email;
	String role;	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}	
}