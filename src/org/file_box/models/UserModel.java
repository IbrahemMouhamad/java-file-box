package org.file_box.models;

public class UserModel {
	// user name
	private String userName;
	// user hashed password
	private String password;
	 
	public UserModel () {}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
	 
}
