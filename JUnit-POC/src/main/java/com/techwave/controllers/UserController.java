package com.techwave.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techwave.model.dao.IUser1Dao;
import com.techwave.model.pojo.User;

@RestController
public class UserController
{
	@Autowired
	IUser1Dao  user1Dao;
	
	@GetMapping("/getAll")
	public List<User> getAll()
	{
		return user1Dao.getAll();
	}
	
	@GetMapping("/user/{id}")
	public User getByEmployeeId(@PathVariable("id") int id)
	{
		return user1Dao.getByEmployeeId(id);
	}
	
	@PostMapping("/insertData")
	public String insertUser(@RequestBody User u)
	{
		return user1Dao.insertUser(u);
	}
	
	@PutMapping("/update")
	public String updateUser(@RequestBody User u)
	{
		return user1Dao.updateUser(u);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id)
	{
		return user1Dao.deleteUser(id);
	}
	
	@GetMapping("byrole/{role}")
	public List<User> getByRole(@PathVariable("role") String role)
	{
		return user1Dao.getByRole(role);
	}
	
}
