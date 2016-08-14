package com.wan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.wan.db.DBHelper;

public class MyDaoImpl implements MyDao {

	@Override
	public String delete(String sql, Object[] params, Connection conn) throws Exception{
		int r = execUpdate(sql, params, conn);
		String result = null;
		if(r>0)
			result = "删除成功";
		else
			result = "删除失败";
		return result;
	}

	@Override
	public String insert(String sql, Object[] params, Connection conn) throws Exception{
		int r = execUpdate(sql, params, conn);
		String result = null;
		if(r>0)
			result = "插入成功";
		else
			result = "插入失败";
		return result;
	}

	@Override
	public String modify(String sql, Object[] params, Connection conn) throws Exception{
		int r = execUpdate(sql, params, conn);
		String result = null;
		if(r>0)
			result = "修改成功";
		else
			result = "修改失败";
		return result;
	}

	@Override
	public ResultSet query(String sql, Object[] params, Connection conn) throws Exception{
		ResultSet rs = null;
		PreparedStatement ptmt = exec(sql, params, conn);
		rs = ptmt.executeQuery();
		return rs;
	}
	
	private int execUpdate(String sql, Object[] params, Connection conn) throws Exception {
		int result = -1;
		PreparedStatement ptmt = exec(sql, params, conn);
		result = ptmt.executeUpdate();
		return result;
	}

	
	private PreparedStatement exec(String sql, Object[] params, Connection conn) throws Exception {
		PreparedStatement ptmt = DBHelper.getPreparedStatement(sql, conn);
		if(null != params){
			int i = 1;
			for (Object o : params) {
				ptmt.setObject(i, o);
				i++;
			}
		}
		return ptmt;
	}

}
