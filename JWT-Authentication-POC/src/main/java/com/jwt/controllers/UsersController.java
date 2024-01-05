package com.jwt.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.User;
import com.jwt.service.UsersService;

@RestController
@RequestMapping("/home")
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	
	//	http://localhost:8080/home/users
	@GetMapping("/users")
	public List<User> userData() {
		return  this.usersService.getUsers();		
	}
	
	@GetMapping("/loggedinuser")
	public String getLoggedinUser(Principal principal) {
		return principal.getName();		
	}
}