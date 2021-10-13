package com.banking.dao;

import java.sql.SQLException;
import java.util.List;

import com.banking.exceptions.UserDoesNotExistException;
import com.banking.models.Customer;
import com.banking.models.Employee;
import com.banking.models.bankingUser;

public interface UserDao {
	
	// List<bankingUser> getAllUsers();
	
	Customer getCustomerByUsername(String username);
	Employee getEmpByUsername(String username);
	
	List<Customer> getAllCusts() throws SQLException;
	
	void createCustUser(Customer u) throws SQLException;
	void createEmpUser(Employee u) throws SQLException;
	Customer getCustomerByUsernameEmpSearch(String username);
	
	Customer updateCust(String username);
	String updateCust2(String fieldname, int customerID, String input);
	
	int deleteCust(String username);

}
