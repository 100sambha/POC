package com.techwave.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techwave.model.pojo.User;

@Service
public class User1Dao implements IUser1Dao
{
	@Autowired
	UserRepository userRepository ;

	@Override
	public List<User> getAll() {
		List<User> u = userRepository.findAll();
		return u;
	}

	@Override
	public User getByEmployeeId(int id) {
		Optional<User> u = userRepository.findById(id);
		if(u.isPresent())
			return u.get();
		return null;
	}

	@Override
	public String insertUser(User u) {
		Optional<User> u1 = userRepository.findById(u.getUserId());
		if(!(u1.isPresent()))
		{			
			userRepository.save(u);
			return "1 Row Inserted";
		}
		return "User already exist";
	}

	@Override
	public String updateUser(User u) {
		Optional<User> u1 = userRepository.findById(u.getUserId());
		if(u1.isPresent())
		{			
			userRepository.save(u);
			return "1 Row Updated";
		}
		return "User does not exist";
	}

	@Override
	public String deleteUser(int id) {
		Optional<User> u1 = userRepository.findById(id);
		if(u1.isPresent())
		{			
			userRepository.deleteById(id);
			return "1 Row Deleted";
		}
		return "User does not exist";
	}

	@Override
	public List<User> getByRole(String role) {
		return userRepository.findByRole(role);
	}
	
}
