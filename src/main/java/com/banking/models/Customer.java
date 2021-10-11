package com.banking.models;

public class Customer extends bankingUser{
	
	private double checkingBalance;
	private double savingsBalance;
	private boolean wantsChecking;
	private boolean wantsSavings;
	private String loginType;
	
	public Customer() {
		super();
	}
	
	public Customer(String firstname, String lastname, String username, String password) {
		super(firstname, lastname, username, password);
		this.wantsChecking = false;
		this.wantsSavings = false;
		this.loginType = "c";
	}
	
	public Customer(int id, String firstname, String lastname, String username, String password) {
		super(id, firstname, lastname, username, password);
		this.wantsChecking = false;
		this.wantsSavings = false;
		this.loginType = "c";
		}

	public double getCheckingBalance() {
		return checkingBalance;
	}


	public void setCheckingBalance(double checkingBalance) {
		this.checkingBalance = checkingBalance;
	}


	public double getSavingsBalance() {
		return savingsBalance;
	}


	public void setSavingsBalance(double savingsBalance) {
		this.savingsBalance = savingsBalance;
	}


	public boolean isHasChecking() {
		return wantsChecking;
	}


	public void setHasChecking(boolean hasChecking) {
		this.wantsChecking = hasChecking;
	}


	public boolean isHasSavings() {
		return wantsSavings;
	}


	public void setHasSavings(boolean hasSavings) {
		this.wantsSavings = hasSavings;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	
	
	
}
