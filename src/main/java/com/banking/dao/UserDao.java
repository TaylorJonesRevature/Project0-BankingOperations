package com.banking.dao;

import java.sql.SQLException;
import java.util.List;

import com.banking.models.Customer;
import com.banking.models.Employee;
import com.banking.models.bankingUser;

public interface UserDao {
	
	// List<bankingUser> getAllUsers();
	
	Customer getCustByUsername(String username);
	Employee getEmpByUsername(String username);
	
	void createCustUser(Customer u) throws SQLException;
	void createEmpUser(Employee u) throws SQLException;
	
	// void updateUser(bankingUser u);
	
	// void deleteUser(bankingUser u);

}
