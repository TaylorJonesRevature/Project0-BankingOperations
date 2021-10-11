package com.banking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectingUtil {

	private static ConnectingUtil cu;
	private static Properties prop = new Properties();
	
	private ConnectingUtil() {
		
	}
	
	public static synchronized ConnectingUtil getConnetingUtil() {
		if(cu == null) {
			cu = new ConnectingUtil();
		}
		return cu;
	}
	
	public Connection getConnection() {
		ClassLoader classloader = getClass().getClassLoader();
		InputStream is = classloader.getResourceAsStream("jdbc.properties");
		String url = "";
		String password = "";
		String username = "";
		
		try {
			prop.load(is);
			url = (String)prop.getProperty("url");
			username = (String)prop.getProperty("username");
			password = (String)prop.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Connection con;
		try {
			con = DriverManager.getConnection(url, username, password);
			return con;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
