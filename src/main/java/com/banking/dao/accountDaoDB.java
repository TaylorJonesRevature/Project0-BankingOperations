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
import com.banking.utils.ConnectingUtil;

public class accountDaoDB implements accountDao {

	ConnectingUtil conUtil = ConnectingUtil.getConnetingUtil();

	public void createAccount(Customer c) throws SQLException {
		Connection con = conUtil.getConnection();

		String sql = "INSERT into accounts(user_id, balance_checking, balance_savings) values" + "(?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, c.getId());
		ps.setDouble(2, c.getCheckingBalance());
		ps.setDouble(3, c.getSavingsBalance());

		ps.execute();

	}
	
	public void createPendingAccount(Customer c) throws SQLException {
		Connection con = conUtil.getConnection();

		String sql = "INSERT into pending_accounts(user_id) values" + "(?)";

		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, c.getId());

		ps.execute();

	}
	
	public String deny(int i) throws SQLException {
		Connection con = conUtil.getConnection();
		String username = null; 
		
		String sql = "select username from users inner join pending_accounts on users.id = pending_accounts.user_id where pending_id = " + i + ";";
		
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery(sql);
		
		while(rs.next()) {
		username = rs.getString(1);
		}
		String sql2 = "DELETE FROM pending_accounts WHERE pending_id = " + i + ";";
		
		PreparedStatement ps = con.prepareStatement(sql2);
		
		ps.execute();
		
		return username;
		}
	
	public String approveChecking(int i) throws SQLException {
		Connection con = conUtil.getConnection();
		String username = null; 
		int user_id = 0;
		
		String sql = "select username from users inner join pending_accounts on users.id = pending_accounts.user_id where pending_id = " + i + ";";
		
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery(sql);
		
		while(rs.next()) {
		username = rs.getString(1);
		}
	
		
		String sql2 = "select user_id from pending_accounts where pending_id = " + i + ";";
		
		s = con.createStatement();
		rs = s.executeQuery(sql2);
		
		while(rs.next()) {
			user_id = rs.getInt(1);
		}
		
		String insertInto = null;
		
		insertInto = "insert into accounts (user_id, balance_checking, balance_savings) values"
				+ "(?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(insertInto);

		ps.setInt(1, user_id);
		ps.setDouble(2, 0.00);
		ps.setDouble(3, 0.00);

		ps.execute();
		
		String sql3 = "DELETE FROM pending_accounts WHERE pending_id = " + i + ";";
		
		ps = con.prepareStatement(sql3);
		
		ps.execute();
		
		return username;
		
	}
	
	public List<Application> applicationView() throws SQLException {
		List<Application> applicationList = new ArrayList<Application>();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "select * from users inner join pending_accounts on users.id = pending_accounts.user_id";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				applicationList.add(new Application(rs.getInt(7), rs.getString(4)));
			}
			
			return applicationList;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
