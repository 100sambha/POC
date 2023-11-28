package com.techwave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.techwave.model.dao.IUser1Dao;
import com.techwave.model.dao.UserRepository;
import com.techwave.model.pojo.User;

@SpringBootTest
class SpringBootDemoApplicationTests {
	@Autowired
	IUser1Dao user1Dao;

	@MockBean
	UserRepository userRepository; // 
//	mock(UserRepository.class);

	@Test
	public void TestgetAll() {
		when(userRepository.findAll())
				.thenReturn(Stream
						.of(new User(1, "sam", "admin"), new User(2, "Ravi", "Associate"),
								new User(3, "Krishna", "admin"), new User(4, "Ram", "Associate"))
						.collect(Collectors.toList()));
		assertEquals(4, user1Dao.getAll().size());
		
		verify(userRepository).findAll();
	}

	@Test
	public void TestgetEmployeeById() {
		User u = new User(1, "sam", "admin");
		when(userRepository.findById(1)).thenReturn(Optional.of(u));
		assertSame(u, user1Dao.getByEmployeeId(1));
	}

	@Test
	public void TestinsertUser() {
		User u = new User(1, "sam", "admin");//creating Obj
		
		when(userRepository.save(u)).thenReturn(u);//after creating obj saving in userRepository and it will return created Obj
		
		assertEquals("1 Row Inserted", user1Dao.insertUser(u));
		
		when(userRepository.findById(1)).thenReturn(Optional.of(u));
		
		assertEquals("User already exist", user1Dao.insertUser(u));
	}

	@Test
	public void updateUser() {
		User U = new User(1, "A", "Admin");
		User u1 = new User(76, "shg", "hsg");
		when(userRepository.findById(1)).thenReturn(Optional.of(U));
		when(userRepository.findById(76)).thenReturn(Optional.empty());
		assertEquals("1 Row Updated", user1Dao.updateUser(U));
		assertEquals("User does not exist", user1Dao.updateUser(u1));
		verify(userRepository).findById(1);
		verify(userRepository).save(U);
	}

	@Test
	public void deleteUser() {
		User U = new User(1, "A", "Admin");
		when(userRepository.findById(1)).thenReturn(Optional.of(U));
		assertEquals("1 Row Deleted", user1Dao.deleteUser(1));
		verify(userRepository).findById(1);
	}
}