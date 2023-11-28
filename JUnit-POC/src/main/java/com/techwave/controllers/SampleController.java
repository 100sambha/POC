package com.techwave.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techwave.model.pojo.User;

@RestController
public class SampleController
{
	private static List<User> list=new ArrayList<User>(Arrays.asList(new User(1,"sambha","Associate"),
			new User(2,"jay","Admin"),
			new User(3,"Ram","Associate"),
			new User(3,"Shyam","Associate"),
			new User(3,"Shree","Associate"),
			new User(3,"Kartik","Admin")
	));
	
	@GetMapping("/test")
	public List<User> getAll() {
		return list;
	}
	@GetMapping("/test/{id}")
	public List<User> getById(@PathVariable("id") int id)
	{
		
		return list.stream().filter(i->i.getUserId()==id).collect(Collectors.toList());
	}
	@GetMapping("/test1/{name}")
	public List<User> getByName(@PathVariable("name") String name)
	{
		return list.stream().filter(i->i.getUserName().equals(name)).collect(Collectors.toList());
	}
	
	@PostMapping("/test")
	public String addUser(@RequestBody User u)
	{
		list.add(u);
		return "Success";
	}
	
	@PutMapping("/test/{id}")
	private String	updateUser(@RequestBody User u,@PathVariable int id)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getUserId()==id)
			{
				list.get(i).setUserName(u.getUserName());
				list.get(i).setRole(u.getRole());
			}
		}
		return "User updated";
	}
	
	@DeleteMapping("test/{id}")
	public String deleteUser(@PathVariable int id)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getUserId()==id)
			{
				list.remove(i);
				return "User Deleted";
			}
		}
		return "User Not Found";
	}
	
}
