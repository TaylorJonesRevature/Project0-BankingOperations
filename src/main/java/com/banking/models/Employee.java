package com.banking.models;

public class Employee extends bankingUser {
	
	private String loginType;

	public Employee(String firstname, String lastname, String username, String password) {
		super(firstname, lastname, username, password);
		this.loginType = "e";
	}
	
	public Employee() {
		super();
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	
}
