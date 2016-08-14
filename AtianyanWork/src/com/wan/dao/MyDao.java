package com.wan.dao;

import java.sql.Connection;
import java.sql.ResultSet;

public interface MyDao {
	
	public String insert(String sql, Object[] params, Connection conn) throws Exception;
	public String delete(String sql, Object[] params, Connection conn) throws Exception;
	public String modify(String sql, Object[] params, Connection conn) throws Exception;
	public ResultSet query(String sql, Object[] params, Connection conn) throws Exception;
	
}
