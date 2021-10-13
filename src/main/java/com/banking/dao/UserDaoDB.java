package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.banking.models.Application;
import com.banking.models.Customer;
import com.banking.models.Employee;
import com.banking.models.bankingUser;
import com.banking.utils.ConnectingUtil;

public class UserDaoDB implements UserDao {

	ConnectingUtil conUtil = ConnectingUtil.getConnetingUtil();

	// passing new customer to database. Customer inherits LoginType 'c'.
	public void createCustUser(Customer u) throws SQLException {
		Connection con = conUtil.getConnection();

		String sql = "INSERT INTO users(first_name, last_name, username, password, login_type) values" + "(?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setString(1, u.getFirstname());
		ps.setString(2, u.getLastname());
		ps.setString(3, u.getUsername());
		ps.setString(4, u.getPassword());
		ps.setString(5, u.getLoginType());

		ps.execute();
	}

	// passing new employee to database. Employee inherits LoginType 'e'.

	public void createEmpUser(Employee u) throws SQLException {
		Connection con = conUtil.getConnection();

		String sql = "INSERT INTO employees(first_name, last_name, username, password, login_type) values"
				+ "(?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setString(1, u.getFirstname());
		ps.setString(2, u.getLastname());
		ps.setString(3, u.getUsername());
		ps.setString(4, u.getPassword());
		ps.setString(5, u.getLoginType());

		ps.execute();
	}

	public Customer getCustomerByUsernameEmpSearch(String username) {
		Customer c = new Customer();
		try {

			Connection con = conUtil.getConnection();
			String sql = "select * from users inner join accounts on id = user_id where username = '" + username + "';";

			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				c.setId(rs.getInt(1));
				c.setFirstname(rs.getString(2));
				c.setLastname(rs.getString(3));
				c.setUsername(rs.getString(4));
				c.setPassword(rs.getString(5));
				c.setCheckingBalance(rs.getDouble(9));
				c.setSavingsBalance(rs.getDouble(10));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public Customer getCustomerByUsername(String username) {
		Customer cust = new Customer();

		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM users WHERE users.username = '" + username + "'";

			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				cust.setId(rs.getInt(1));
				cust.setFirstname(rs.getString(2));
				cust.setLastname(rs.getString(3));
				cust.setUsername(rs.getString(4));
				cust.setPassword(rs.getString(5));
			}
			return cust;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<Customer> getAllCusts() throws SQLException {
		List<Customer> custList = new ArrayList<Customer>();
		Connection con = conUtil.getConnection();
		String sql = "Select * from users full join accounts on id = user_id order by last_name, first_name;";

		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			custList.add(new Customer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getDouble(9), rs.getDouble(10)));
		}

		return custList;
	}

	public Employee getEmpByUsername(String username) {
		Employee emp = new Employee();

		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM employees WHERE employees.username = '" + username + "'";

			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				emp.setId(rs.getInt(1));
				emp.setFirstname(rs.getString(2));
				emp.setLastname(rs.getString(3));
				emp.setUsername(rs.getString(4));
				emp.setPassword(rs.getString(5));
			}
			return emp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Customer updateCust(String username) {
		Customer newCust = new Customer();
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "Select * from users left join accounts on users.id = accounts.user_id where username  = '" + username + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				newCust.setId(rs.getInt(1));
				newCust.setFirstname(rs.getString(2));
				newCust.setLastname(rs.getString(3));
				newCust.setUsername(rs.getString(4));
				newCust.setPassword(rs.getString(5));
				newCust.setCheckingBalance(rs.getDouble(9));
				newCust.setSavingsBalance(rs.getDouble(10));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} return newCust;
	}
	
	public String updateCust2(String fieldname, int customerID, String input){
		String returnString = null;
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "UPDATE users set " + fieldname + " = '" + input + "' where id = " + customerID + "";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			
			String sql2 = "SELECT " + fieldname + " from users where id = " + customerID;
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql2);
			
			while (rs.next()) {
				returnString = rs.getString(1);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	public int deleteCust(String username) {
		int id = 0;
		try {
			
			Connection con = conUtil.getConnection();
			String sql = "SELECT id from users WHERE users.username = '" + username + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				id = rs.getInt(1);
			}
			
			String sql2 = "DELETE from users WHERE users.username = '" + username + "'";
			
			PreparedStatement ps = con.prepareStatement(sql2);
			ps.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

}
