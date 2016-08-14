package com.wan.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.wan.dao.MyDaoImpl;
import com.wan.db.DBHelper;
import com.wan.entity.User;

public class MyService {

	private MyDaoImpl dao = new MyDaoImpl();
	
	public String add(String sql, Object[] params) throws Exception{
		Connection conn = DBHelper.getConnection(); 
		String result = dao.insert(sql, params, conn);
		DBHelper.closeConnection(conn);
		return result;
	}
	
	public String delete(String sql, Object[] params) throws Exception{
		Connection conn = DBHelper.getConnection();
		String result = dao.delete(sql, params, conn);
		DBHelper.closeConnection(conn);
		return result;
	}
	
	public String modify(String sql, Object[] params) throws Exception{
		Connection conn = DBHelper.getConnection();
		String result = dao.modify(sql, params, conn);
		DBHelper.closeConnection(conn);
		return result;
	}
	
	public List<User> queryS(String sql, Object[] params) throws Exception{
		List<User> list = new ArrayList<User>();
		Connection conn = DBHelper.getConnection();
		ResultSet rs = dao.query(sql, params, conn);
		while(rs.next()){
			User s = new User();
			s.setUser_id(rs.getInt("user_id"));
			s.setUser_name(rs.getString("user_name"));
			s.setUser_ip(rs.getString("user_ip"));
			s.setUser_port(rs.getInt("user_port"));
			s.setUser_status(rs.getInt("user_status"));
			list.add(s);
		}
		DBHelper.closeConnection(conn);
		return list;
	}
}
