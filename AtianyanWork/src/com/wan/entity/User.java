package com.wan.entity;

public class User {
	private int user_id;
	private String user_name;
	private String user_ip;
	private int user_port;
	private int user_status;
	
	public int getUser_status() {
		return user_status;
	}
	public void setUser_status(int user_status) {
		this.user_status = user_status;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public int getUser_port() {
		return user_port;
	}
	public void setUser_port(int user_port) {
		this.user_port = user_port;
	}
	
}
