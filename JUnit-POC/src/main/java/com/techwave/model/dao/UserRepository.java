package com.techwave.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techwave.model.pojo.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	List<User> findByRole(String role);
}