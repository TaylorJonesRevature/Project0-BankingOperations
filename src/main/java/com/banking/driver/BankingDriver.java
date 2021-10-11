package com.banking.driver;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.banking.dao.UserDao;
import com.banking.dao.UserDaoDB;
import com.banking.dao.accountDao;
import com.banking.dao.accountDaoDB;
import com.banking.exceptions.DuplicateUsernameException;
import com.banking.exceptions.LoginFailedException;
import com.banking.exceptions.UserDoesNotExistException;
import com.banking.logging.Logging;
import com.banking.models.Application;
import com.banking.models.Customer;
import com.banking.models.Employee;
import com.banking.userServices.accountService;
import com.banking.userServices.bankingUserService;

public class BankingDriver {
	private static UserDao uDao = new UserDaoDB();
	private static accountDao aDao = new accountDaoDB();
	private static bankingUserService uServ = new bankingUserService(uDao);
	private static accountService aServ = new accountService(aDao);

	public static void main(String[] args) throws LoginFailedException {
		Scanner scan = new Scanner(System.in);
		Customer c = null;
		Employee emp = null;
		boolean doneWithLogin = false;
		boolean doneWithMenu = false;
		
		// Opening introductory statements
		System.out.println("Welcome to the GloboFund Banking System.");
		System.out.print("Are you a Customer (press 'c'), an Employee (press 'e'), or Exit (press x)?    ");
		char choice = scan.nextLine().toLowerCase().charAt(0);

		// while loop enforcing login
		while (doneWithLogin == false) {

				switch (choice) {
				
				// case x
				case 'x':
					doneWithLogin = true;
					System.out.println("Goodbye.");
					break;
					
				// Customer case
				case 'c':
					System.out.print("SignUp (press 's'), Login (press 'l') or Exit (press 'x')?     ");
					char choice2 = scan.nextLine().toLowerCase().charAt(0);

					if (choice2 == 's') {
						System.out.print("Please enter your first name: ");
						String firstnameCust = scan.nextLine();
						System.out.print("Please enter your last name: ");
						String lastnameCust = scan.nextLine();
						System.out.print("Please enter a username: ");
						String usernameCust = scan.nextLine();
						System.out.print("Please enter a password: ");
						String passwordCust = scan.nextLine();
						System.out.println();
						try {
							uServ.signUpCust(firstnameCust, lastnameCust, usernameCust, passwordCust);
						} catch (DuplicateUsernameException e) {
							System.out.println();
							System.out.println("______________________________________");
							System.out.println("Invalid SignUp. Please try again.");
							System.out.println("______________________________________");
							System.out.println();
							continue;
						}
					} else if(choice2 == 'x') {
						doneWithLogin = true;
						System.out.println("Goodbye.");
						break;
					}
					System.out.println();
					System.out.println("Please sign in: ");
					System.out.println();
					System.out.println("Enter your username: ");
					String username = scan.nextLine();
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					System.out.println("______________________________________");
					try {
					c = uServ.loginInCust(username, password);
					} catch(UserDoesNotExistException u) {
						System.out.println("User does not exist. Please sign up.");
						continue;
					} catch(LoginFailedException e) {
						System.out.println("Your username or password are incorrect. Try again.");
						continue;
					}
					System.out.println("You are now signed in, " + c.getFirstname() + ".");
					System.out.println("______________________________________");
					doneWithLogin = true;
					break;

				// -----------------------------------------------------------------------------------------------
				// employee case
				// -----------------------------------------------------------------------------------------------

				case 'e':
					System.out.println("SignUp (press 's'), Login (press 'l') or exit (press 'x')?    ");
					char choice3 = scan.nextLine().toLowerCase().charAt(0);

					if (choice3 == 's') {
						System.out.print("Please enter your first name: ");
						String firstnameEmp = scan.nextLine();
						System.out.print("Please enter your last name: ");
						String lastnameEmp = scan.nextLine();
						System.out.print("Please enter a username: ");
						String usernameEmp = scan.nextLine();
						System.out.println("Please enter a password: ");
						String passwordEmp = scan.nextLine();
						try {
							uServ.signUpEmp(firstnameEmp, lastnameEmp, usernameEmp, passwordEmp);
						} catch (DuplicateUsernameException e) {
							System.out.println();
							System.out.println("______________________________________");
							System.out.println("Invalid SignUp. Please try again.");
							System.out.println("______________________________________");
							System.out.println();
							continue;
						}
						
					} else if(choice3 == 'x')
					{
						doneWithLogin = true;
						System.out.println("Goodbye.");
						break;
					}
					System.out.println("Please sign in: ");
					System.out.println();
					System.out.println("Enter your username: ");
					username = scan.nextLine();
					System.out.print("Enter your password: ");
					password = scan.nextLine();
					System.out.println("______________________________________");
					try {
					emp = uServ.loginInEmp(username, password);
					} catch(UserDoesNotExistException e) {
						System.out.println("User does not exist. Please sign up.");
						break;
					} catch(LoginFailedException e) {
						System.out.println("Your username or password are incorrect. Try again.");
						continue;
					}
					System.out.println("You are now signed in, " + emp.getFirstname() + ".");
					System.out.println("______________________________________");
					doneWithLogin = true;
					break;
					
					
					default:
					System.out.println("Your input was not recognized. Please run system again.");
					doneWithLogin = true;
					break;
				}
			} 

		// ===================================================================================
		// beginning of user menu and transaction logic
		// ===================================================================================

		while (doneWithMenu == false) {
			
			// ===================================================================================
			// beginning of customer menu and transaction logic
			// ===================================================================================
			
			if (c != null & emp == null) {
				System.out.println(
						"Would you like to: open an account (press 'a'), deposit funds (press 'd'), transfer funds (press 't') or exit (press 'x')?    ");
				char choice4 = scan.nextLine().toLowerCase().charAt(0);
				switch (choice4) {
				
				//exit case
				case 'x':
					doneWithMenu = true;
					break;

				// account opening case

				case 'a':
					System.out.println(
							"What type of account would you like to open? Checking (press 'c') or Savings (press 's') or both (press 'b')?   ");
					char choice5 = scan.nextLine().toLowerCase().charAt(0);
					if (choice5 == 'c') {
						aServ.openAccount(c);
						System.out.println("Your account application has been received and will be evaluated by our staff.");
					}
					continue;
				}
				doneWithMenu = true;
			} 
			// ===================================================================================
			// beginning of employee menu and transaction logic
			// ===================================================================================
			else if(c == null & emp != null) {
				System.out.println("Would you like to view all users (press 'v'), view a specific user (press 's') or approve/deny applications (press 'a')?    ");
				char choice5 = scan.nextLine().toLowerCase().charAt(0);
				switch(choice5) {
				case 'a':
					System.out.println("The following applications are pending:    ");
					List<Application> Apps = aServ.applicationsView();
					System.out.println(Apps);
					System.out.println();
					System.out.print("Would you like to approve (press 'a') or decline (press 'd') an application?    ");
					char choice6 = scan.nextLine().toLowerCase().charAt(0);
					// other dialogue below for first choice
					
					// dialogue for case a, case d
					
					switch(choice6) {
					case 'd':
						System.out.print("Select the number *|pendingID NUMBER|* of the application you'd like to deny:    ");
						int choice7 = Integer.parseInt(scan.nextLine());
						String denyName = aServ.applicationDeny(choice7);
						System.out.println("You've denied the application of: " + denyName);
						System.out.println("That was a good choice. Thank you for protecting the bank.");
						System.out.println("______________________________________");
						continue;
						
						// dialogue for case a, case a
						
					case 'a':
						System.out.print("Select the number *|pendingID NUMBER|* of the application you'd like to approve:   ");
						int choice8 = Integer.parseInt(scan.nextLine());
						String approveName = aServ.checkingApplicationApprove(choice8);
						System.out.println("You've approved the application of: " + approveName);
						System.out.println("They're going to be very happy with their new account.");
						System.out.println("______________________________________");
						continue;
					}
					
				}
				
			}
			doneWithMenu = true;
		}

	}
}