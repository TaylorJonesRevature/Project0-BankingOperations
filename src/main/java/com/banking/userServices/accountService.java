package com.banking.userServices;

import java.sql.SQLException;
import java.util.List;

import com.banking.dao.UserDao;
import com.banking.dao.accountDao;
import com.banking.dao.accountDaoDB;
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
			e.getStackTrace();
			Logging.logger.warn("SQL Error detected.");
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
}
