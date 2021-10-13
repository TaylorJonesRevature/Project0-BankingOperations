package com.banking.userServices;

import java.sql.SQLException;
import java.util.List;

import com.banking.dao.UserDao;
import com.banking.dao.accountDao;
import com.banking.dao.accountDaoDB;
import com.banking.exceptions.AccountOverdrawnException;
import com.banking.exceptions.DuplicateAccountSetupsException;
import com.banking.logging.Logging;
import com.banking.models.Application;
import com.banking.models.Customer;

public class accountService{
	private accountDao aDao;
	
	public accountService(accountDao aDao){
		this.aDao = aDao;
	}
	
	public void openAccount(Customer c)
	{
		try{
			aDao.createPendingAccount(c);
		} catch(SQLException e) {
			Logging.logger.warn("Duplicate accounts setups detected.");
			throw new DuplicateAccountSetupsException();
		};
	}
	
	public List<Application> applicationsView() {
		try {
			return aDao.applicationView();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String applicationDeny(int i) {
		String username = null;
		try {
			username = aDao.deny(i);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return username;
	}
	
	
	public String checkingApplicationApprove(int i) {
		String username = null;
		try {
			username = aDao.approveChecking(i);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return username;
	} 
	
	public double withdrawal(String username, double i) throws AccountOverdrawnException {
		double checkingBalance = 0;
		try {
			checkingBalance = aDao.withdrawalFunds(username, i);
			if(checkingBalance == 0) {
				throw new AccountOverdrawnException();
			}
			return checkingBalance;
		} catch (SQLException e) {
			Logging.logger.warn("SQL error detected in withdrawal step");
			e.printStackTrace();
		}
		return checkingBalance;
	}
	
	public double deposit(String username, double i){
		double checkingBalance = 0;
		try {
			checkingBalance = aDao.depositFunds(username, i);
		} catch (SQLException e) {
			Logging.logger.warn("SQL error detected in deposit step");
			e.printStackTrace();
		}
		return checkingBalance;
	}
}
