package com.banking.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Customer extends bankingUser{
	
	NumberFormat formatter = new DecimalFormat("#0.00");
	
	private double checkingBalance;
	private double savingsBalance;
	private String loginType;
	

	public Customer() {
		super();
	}
	
	public Customer(String firstname, String lastname, String username, String password, double checkingBalance, double savingsBalance) {
		super(firstname, lastname, username, password);
		this.checkingBalance = checkingBalance;
		this.savingsBalance = savingsBalance;
		this.loginType = "c";
	}
	
	public Customer(int id, String firstname, String lastname, String username, String password, double checkingBalance, double savingsBalance) {
		super(firstname, lastname, username, password);
		this.checkingBalance = checkingBalance;
		this.savingsBalance = savingsBalance;
		this.loginType = "c";
	}
	
	
	public Customer(String firstname, String lastname, String username, String password) {
		super(firstname, lastname, username, password);
		this.loginType = "c";
	}
	
	public Customer(int id, String firstname, String lastname, String username, String password) {
		super(id, firstname, lastname, username, password);
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


	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return super.toString() + ", checkingBalance= $" + formatter.format(checkingBalance) + ", savingsBalance= $" + formatter.format(savingsBalance) +"]";
	}
	
	
	
	
}
