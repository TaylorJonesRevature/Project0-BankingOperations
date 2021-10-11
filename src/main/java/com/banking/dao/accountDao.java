package com.banking.dao;

import java.sql.SQLException;
import java.util.List;

import com.banking.models.Application;
import com.banking.models.Customer;

public interface accountDao {

	void createAccount(Customer c) throws SQLException;
	void createPendingAccount(Customer c) throws SQLException;
	public List<Application> applicationView() throws SQLException;
	public String deny(int i) throws SQLException;
	public String approveChecking(int i) throws SQLException;
}
