package com.techwave.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserDetails")
public class User
{
	@Id
	private int userId;
	@Column(length=25, name="UserName")
	private String userName;
	@Column(length=25, name="Role")
	private String role;
	
	public User() {}

	public User(int userId, String userName, String role)
	{
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}