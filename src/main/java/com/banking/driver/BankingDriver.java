package com.banking.driver;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.banking.dao.UserDao;
import com.banking.dao.UserDaoDB;
import com.banking.dao.accountDao;
import com.banking.dao.accountDaoDB;
import com.banking.exceptions.AccountOverdrawnException;
import com.banking.exceptions.DuplicateAccountSetupsException;
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

	public static void main(String[] args) throws LoginFailedException, SQLException {
		// scanner for char
		Scanner scan = new Scanner(System.in);
		// scanner for double
		Scanner scan2 = new Scanner(System.in);
		// scanner for string - this was due to an odd error in control flow only fixed
		// by this...
		Scanner scan3 = new Scanner(System.in);
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
						System.out.println("You are now registered as: " + usernameCust);
					} catch (DuplicateUsernameException e) {
						System.out.println();
						System.out.println("______________________________________");
						System.out.println("Invalid SignUp. Please try again.");
						System.out.println("______________________________________");
						System.out.println();
						continue;
					}
				} else if (choice2 == 'x') {
					doneWithLogin = true;
					System.out.println("Goodbye.");
					break;
				} else if (choice2 == 'l') {
					System.out.println();
					System.out.println("Please sign in: ");
					System.out.println();
					System.out.print("Enter your username: ");
					String username = scan.nextLine();
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					System.out.println("______________________________________");
					try {
						c = uServ.loginInCust(username, password);
					} catch (UserDoesNotExistException u) {
						System.out.println("User does not exist. Please sign up.");
						continue;
					} catch (LoginFailedException e) {
						System.out.println("Your username or password are incorrect. Try again.");
						continue;
					}
					System.out.println("You are now signed in, " + c.getFirstname() + ".");
					System.out.println("______________________________________");
					doneWithLogin = true;
					break;
				} else {
					System.out.println("Your input was not recognized. Please try again.");
					continue;
				}

				// -----------------------------------------------------------------------------------------------
				// employee case
				// -----------------------------------------------------------------------------------------------

			case 'e':
				System.out.print("SignUp (press 's'), Login (press 'l') or exit (press 'x')?    ");
				char choice3 = scan.nextLine().toLowerCase().charAt(0);

				if (choice3 == 's') {
					System.out.print("Please enter your first name: ");
					String firstnameEmp = scan.nextLine();
					System.out.print("Please enter your last name: ");
					String lastnameEmp = scan.nextLine();
					System.out.print("Please enter a username: ");
					String usernameEmp = scan.nextLine();
					System.out.print("Please enter a password: ");
					String passwordEmp = scan.nextLine();
					System.out.println();
					try {
						uServ.signUpEmp(firstnameEmp, lastnameEmp, usernameEmp, passwordEmp);
						System.out.println("You are now registered as: " + usernameEmp);
					} catch (DuplicateUsernameException e) {
						System.out.println();
						System.out.println("______________________________________");
						System.out.println("Invalid SignUp. Please try again.");
						System.out.println("______________________________________");
						System.out.println();
						continue;
					}

				} else if (choice3 == 'x') {
					doneWithLogin = true;
					System.out.println("Goodbye.");
					break;
				} else if (choice3 == 'l') {
					System.out.println("Please sign in: ");
					System.out.println();
					System.out.print("Enter your username: ");
					String username = scan.nextLine();
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					System.out.println("______________________________________");
					try {
						emp = uServ.loginInEmp(username, password);
					} catch (UserDoesNotExistException e) {
						System.out.println("User does not exist. Please sign up.");
						break;
					} catch (LoginFailedException e) {
						System.out.println("Your username or password are incorrect. Try again.");
						continue;
					}
					System.out.println("You are now signed in, " + emp.getFirstname() + ".");
					System.out.println("______________________________________");
					doneWithLogin = true;
					break;
				} else {
					System.out.println("Your input was not recognized. Please try again.");
					continue;
				}
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
				System.out.print(
						"Would you like to: open an account (press 'a'), deposit funds (press 'd'), withdrawal funds (press 'w') or exit (press 'x')?    ");
				char choice4 = 'a';
				choice4 = scan.nextLine().toLowerCase().charAt(0);
				switch (choice4) {

				// exit case
				case 'x':
					doneWithMenu = true;
					System.out.println("Goodbye.");
					break;

				// account opening case

				case 'a':
					System.out.print("Open a new account? (Press 'y' for yes, 'n' for no)   ");
					char choice5 = scan.nextLine().toLowerCase().charAt(0);
					if (choice5 == 'y') {
						try {
							aServ.openAccount(c);
							Logging.logger.info("New pending account detected.");
							System.out.println(
									"Your account application has been received and will be evaluated by our staff.");
							continue;
						} catch (DuplicateAccountSetupsException e) {
							System.out
									.println("You already have a pending application. Our staff will review it soon.");
							continue;
						}
					} else {
						continue;
					}

					// account deposit case

				case 'd':
					NumberFormat formatter = new DecimalFormat("#0.00");
					double choice6 = 0;
					char choice7 = 'y';
					do {
						try {
							System.out.print("How much would you like to deposit into your account today?    ");
							choice6 = scan2.nextDouble();

							while (choice6 <= 0) {
								System.out.println("Please enter a valid deposit amount.");
								choice6 = scan2.nextDouble();
							}
							double newBalance = aServ.deposit(c.getUsername(), choice6);
							c.setCheckingBalance(newBalance);
							Logging.logger.info("New deposit detected.");
							System.out.println("Your new balance is: $" + formatter.format(newBalance));
							System.out.print("Would you like to make another deposit? (y/n)    ");
							choice7 = scan.nextLine().toLowerCase().charAt(0);
							System.out.println();
						} catch (InputMismatchException e) {
							System.out.println("Please enter a dollar amount in the form of x.xx;");
							choice7 = 'n';
							continue;
						}
					} while (choice7 == 'y');
					continue;
				// account withdrawal case (same method called, just convert to negative #
				// beforehand)

				case 'w':
					formatter = new DecimalFormat("#0.00");
					double choice8 = 0;
					char choice9 = 'y';
					do {
						try {
							System.out.println("How much would you like to withdrawal from your account today?    ");
							choice8 = scan2.nextDouble();
							double newBalance = aServ.withdrawal(c.getUsername(), choice8);
							c.setCheckingBalance(newBalance);
							Logging.logger.info("New withdrawal detected.");
							System.out.println("Your new balance is: $" + formatter.format(newBalance));
							System.out.print("Would you like to make another withdrawal? (y/n)    ");
							choice9 = scan.nextLine().toLowerCase().charAt(0);
						} catch (InputMismatchException e) {
							System.out.println("Please enter a dollar amount in the form of x.xx;");
							choice9 = 'n';
							break;
						} catch (AccountOverdrawnException e) {
							System.out.println("You do not have the funds to complete this transaction.");
							choice9 = 'n';

						}
					} while (choice9 == 'y');
				default:
					System.out.println("Please select one of the menu options.");
					continue;
				}
			}
			// ===================================================================================
			// beginning of employee menu and transaction logic
			// ===================================================================================
			else if (c == null & emp != null) {
				System.out.println(
						"Would you like to view all users (press 'v'), view a specific user (press 's'), approve/deny applications (press 'a'),");
				System.out.print("update a user (press 'u'), delete a user (press 'd'), or exit (press 'x')?   ");
				char choice5 = scan.nextLine().toLowerCase().charAt(0);
				char choice6 = 'a';
				switch (choice5) {

				case 'x':
					doneWithMenu = true;
					System.out.println("Goodbye.");
					break;

				case 'u':
					System.out.print("What username would you like to update?    ");
					String choice12 = scan.nextLine().toLowerCase();
					Customer newCustomer;
					try {
							newCustomer = uServ.updateCustomer(choice12);
							System.out.println("Here is the information we have for: " + newCustomer.getFirstname() + " "
									+ newCustomer.getLastname());
							System.out.println();
							System.out.println(newCustomer);
						} catch (UserDoesNotExistException e) {
						Logging.logger.warn("Employee has searched for a username that does not exist.");
						System.out.println("That username does not exist");
						System.out.println();
						continue;
					}
					System.out.println();
					System.out.println("Which field would you like to update?:");
					System.out.println();
					System.out.println("firstname: (press 'f')");
					System.out.println("lastname: (press 'l')");
					System.out.println("username: (press 'u')");
					System.out.println("password: (press 'p')");
					char choice13 = scan.nextLine().toLowerCase().charAt(0);
					
					switch(choice13) {
					
					case 'f':
						String caseNameF = "first_name";
						String newFirstName;
						System.out.print("What would you like to update the first name to?:   ");
						String firstChoice = scan.nextLine();
						newFirstName = uServ.updateCustomer2(caseNameF, newCustomer.getId(), firstChoice);
						System.out.println("______________________________________");
						System.out.println("You've successfully changed the first name to: " + newFirstName);
						System.out.println("______________________________________");
						System.out.println();
						break;
						
					case 'l':
						String caseNameL = "last_name";
						String newLastName;
						System.out.print("What would you like to update the last name to?:   ");
						String lastChoice = scan.nextLine();
						newLastName = uServ.updateCustomer2(caseNameL, newCustomer.getId(), lastChoice);
						System.out.println("______________________________________");
						System.out.println("You've successfully changed the last name to: " + newLastName);
						System.out.println("______________________________________");
						System.out.println();
						break;
						
					case 'u':
						String caseNameU = "username";
						String newUsername;
						System.out.print("What would you like to update the username to?:   ");
						String userChoice = scan.nextLine();
						newUsername = uServ.updateCustomer2(caseNameU, newCustomer.getId(), userChoice);
						System.out.println("______________________________________");
						System.out.println("You've successfully changed the username to: " + newUsername);
						System.out.println("______________________________________");
						System.out.println();
						break;
						
					case 'p':
						String caseNameP = "password";
						String newPassword;
						System.out.print("What would you like to update the username to?:   ");
						String passChoice = scan.nextLine();
						newPassword = uServ.updateCustomer2(caseNameP, newCustomer.getId(), passChoice);
						System.out.println("______________________________________");
						System.out.println("You've successfully changed the username to: " + newPassword);
						System.out.println("______________________________________");
						System.out.println();
						break;
					} continue;

				case 's':
					System.out.print("What username would you like to search?    ");
					String choice10 = scan.nextLine().toLowerCase();
					Customer newCust = uServ.getCustByUsername(choice10);
					try {
						if (newCust.getId() == 0) {
							throw new UserDoesNotExistException();
						} else {
							System.out.println();
							System.out.println("Here is the information we have for: " + newCust.getFirstname() + " "
									+ newCust.getLastname());
							System.out.println(newCust);
							System.out.println();
						}
					} catch (UserDoesNotExistException e) {
						Logging.logger.warn("Employee has searched for a username that does not exist.");
						System.out.println("That username does not exist. Please try again.");
						System.out.println();
					}
					continue;

				case 'v':
					System.out.println("Here are all users of the Globofund Bank System:   ");
					List<Customer> cust = uServ.getAllCustomers();
					for (int i = 0; i < cust.size(); i++) {
						System.out.println(cust.get(i));
					}
					System.out.println();
					System.out.println();
					continue;

				case 'd':
					System.out.print("What is the username of the user you'd like to delete?    ");
					String choice11 = scan.nextLine().toLowerCase();
					try {
						uServ.deleteUser(choice11);
						System.out.println("You've succesfully deleted the user: " + choice11);
					} catch (UserDoesNotExistException e) {
						Logging.logger.warn("Employee has searched for a username that does not exist.");
						System.out.println("That username does not exist. Please try again.");
						System.out.println();
						continue;
					}
					continue;

				// dialogue for review pending applications

				case 'a':
					System.out.println("The following applications are pending:    ");
					List<Application> Apps = aServ.applicationsView();
					System.out.println(Apps);
					System.out.println();
					System.out
							.print("Would you like to approve (press 'a') or decline (press 'd') an application?    ");
					choice6 = scan.nextLine().toLowerCase().charAt(0);

					// dialogue for deny pending application
					switch (choice6) {
					case 'd':
						System.out.print(
								"Select the number *|pendingID NUMBER|* of the application you'd like to deny:    ");
						int choice7 = Integer.parseInt(scan.nextLine());
						String denyName = aServ.applicationDeny(choice7);
						System.out.println("You've denied the application of: " + denyName);
						System.out.println("That was a good choice. Thank you for protecting the bank.");
						System.out.println("______________________________________");
						continue;

					// dialogue for approve pending application
					case 'a':
						System.out.print(
								"Select the number *|pendingID NUMBER|* of the application you'd like to approve:   ");
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