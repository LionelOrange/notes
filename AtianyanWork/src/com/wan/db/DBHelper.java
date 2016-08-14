package com.wan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class DBHelper {
	
	
	private static String url = "jdbc:mysql://localhost:3306/student";
	private static String user = "root";
	private static String password = "12345678";

	public static Connection getConnection() throws Exception{
		Connection conn = null;
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		
		return conn;
	}
	
	public static PreparedStatement getPreparedStatement(String sql, Connection conn) throws Exception, Exception{
		PreparedStatement ptmt = null;
		ptmt = conn.prepareStatement(sql);
		
		return ptmt;
	}
	
	public static void closeConnection(Connection conn) throws Exception{
		if(null != conn)
			conn.close();		
	}
	
}
