package com.banking.userServices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.dao.UserDao;
import com.banking.exceptions.DuplicateUsernameException;
import com.banking.exceptions.LoginFailedException;
import com.banking.exceptions.UserDoesNotExistException;
import com.banking.logging.Logging;
import com.banking.models.Customer;
import com.banking.models.Employee;

public class bankingUserService {

	private UserDao uDao;

	public bankingUserService(UserDao uDao) {
		this.uDao = uDao;
	}

	public Customer signUpCust(String first, String last, String username, String password)
			throws DuplicateUsernameException {

		Customer c = new Customer(first, last, username, password);

		try {
			uDao.createCustUser(c);
		}

		catch (SQLException e) {
			e.getStackTrace();
			Logging.logger.warn("SQL Error detected.");
			throw new DuplicateUsernameException();
		}
		return c;
	}

	public Customer loginInCust(String username, String password) throws UserDoesNotExistException, SQLException {
		Customer c = uDao.getCustomerByUsername(username);
		if (c.getId() == 0) {
			Logging.logger.warn("User has tried logging in with a username that does not exist.");
			throw new UserDoesNotExistException();
		} else if (!c.getPassword().equals(password)) {
			Logging.logger.warn("User has used an incorrect password");
			throw new LoginFailedException();
		} else {
			Logging.logger.info("User was successfully logged in");
			return c;
		}

	}

	public Employee signUpEmp(String first, String last, String username, String password) {

		Employee emp = new Employee(first, last, username, password);

		try {
			uDao.createEmpUser(emp);
		} catch (SQLException e) {
			e.getStackTrace();
			Logging.logger.warn("SQL Error detected.");
			throw new DuplicateUsernameException();
		}
		return emp;
	}

	public Employee loginInEmp(String username, String password) throws UserDoesNotExistException {
		Employee emp = uDao.getEmpByUsername(username);

		if (emp.getId() == 0) {
			Logging.logger.warn("User has tried logging in with a username that does not exist.");
			throw new UserDoesNotExistException();
		} else if (!emp.getPassword().equals(password)) {
			Logging.logger.warn("User has used an incorrect password");
			throw new LoginFailedException();
		} else {
			Logging.logger.info("User was successfully logged in");
			return emp;
		}
	}

	public List<Customer> getAllCustomers() {
		List<Customer> custList = new ArrayList<Customer>();
		try {
			custList = uDao.getAllCusts();
		} catch (SQLException e) {
			Logging.logger.warn("SQL error detected");
			e.printStackTrace();
		}
		return custList;
	}

	public Customer getCustByUsername(String username) throws UserDoesNotExistException, SQLException {
		Customer newCust = new Customer();
		newCust = uDao.getCustomerByUsernameEmpSearch(username);
		return newCust;
	}
	
	public void deleteUser(String username) throws UserDoesNotExistException{
		int deleteTest;
			deleteTest = uDao.deleteCust(username);
			if(deleteTest == 0) {
				throw new UserDoesNotExistException();
			}
	}
	
	public Customer updateCustomer(String username) throws UserDoesNotExistException{
		Customer newCust = new Customer();
		newCust = uDao.updateCust(username);
		if(newCust.getId() == 0){
			throw new UserDoesNotExistException();
		}
		return newCust;
	}
	
	// updating String variables of Customer
	
	public String updateCustomer2(String fieldName, int customerID, String input) {
		String testString;
		testString = uDao.updateCust2(fieldName, customerID, input);
		return testString;
	}
}
