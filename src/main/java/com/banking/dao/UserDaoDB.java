package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.banking.models.Customer;
import com.banking.models.Employee;
import com.banking.models.bankingUser;
import com.banking.utils.ConnectingUtil;

public class UserDaoDB implements UserDao {

	ConnectingUtil conUtil = ConnectingUtil.getConnetingUtil();
	
	
	// passing new customer to database. Customer inherits LoginType 'c'.
	public void createCustUser(Customer u) throws SQLException {
		Connection con = conUtil.getConnection();
		
		String sql = "INSERT INTO users(first_name, last_name, username, password, login_type) values"
				+ "(?,?,?,?,?)";
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
	
	public Customer getCustByUsername(String username) {
		Customer c = new Customer();
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM users WHERE users.username = '" + username + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				c.setId(rs.getInt(1));
				c.setFirstname(rs.getString(2));
				c.setLastname(rs.getString(3));
				c.setUsername(rs.getString(4));
				c.setPassword(rs.getString(5));
			}
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Employee getEmpByUsername(String username) {
		Employee emp = new Employee();
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM employees WHERE employees.username = '" + username + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
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
	
}
