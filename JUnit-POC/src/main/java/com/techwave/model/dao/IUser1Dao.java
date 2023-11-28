package com.techwave.model.dao;

import java.util.List;

import com.techwave.model.pojo.User;

public interface IUser1Dao {
	
	List<User> getAll();
	User getByEmployeeId(int id);
	String insertUser(User u);
	String updateUser(User u);
	String deleteUser(int id);
	List<User> getByRole(String role);
}