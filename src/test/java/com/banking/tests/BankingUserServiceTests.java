package com.banking.tests;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.banking.dao.UserDao;
import com.banking.dao.UserDaoDB;
import com.banking.dao.accountDao;
import com.banking.dao.accountDaoDB;
import com.banking.exceptions.UserDoesNotExistException;
import com.banking.models.Customer;
import com.banking.userServices.bankingUserService;



public class BankingUserServiceTests {
	


	
	@Test
	public void testSignUpCust() {
		UserDao uDao = new UserDaoDB();
		bankingUserService uServ = new bankingUserService(uDao);
		Customer newCust = new Customer();
		newCust = uServ.signUpCust("Joe", "Schmoe", "jschmoe", "nopass");
		assertEquals("Joe", newCust.getFirstname());
		assertEquals("Schmoe", newCust.getLastname());
		assertEquals("jschmoe", newCust.getUsername());
		assertEquals("nopass", newCust.getPassword());
		
	}
	
	@Test
	public void testLoginCust() throws UserDoesNotExistException, SQLException {
		UserDao uDao = new UserDaoDB();
		bankingUserService uServ = new bankingUserService(uDao);
		Customer newCust = new Customer();
		String username = "jschmoe";
		String password = "nopass";
		newCust = uServ.loginInCust(username, password);
		assertEquals("jschmoe", newCust.getUsername());
		assertEquals("nopass", newCust.getPassword());
		}
	
	}
