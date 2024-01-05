package com.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwt.model.User;

@Service
public class UsersService {
	
	private List<User> userData = new ArrayList<>();

	public UsersService() {
		super();
		userData.add(new User(UUID.randomUUID().toString(),"Sambhaji P","sam@dev.in"));
		userData.add(new User(UUID.randomUUID().toString(),"Shree Ram","ram@dev.in"));
		userData.add(new User(UUID.randomUUID().toString(),"Shree Krishna","krishna@dev.in"));
		userData.add(new User(UUID.randomUUID().toString(),"Shiv Shambho","shiv@dev.in"));
	}
	
	public List<User> getUsers() {
		return this.userData;		
	}
}
