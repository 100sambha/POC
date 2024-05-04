package com.aithentic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.UserCollectionPage;

public class AppTest {
	
	@Test
	public void initializeGraph() {
		final Properties oAuthProperties = new Properties();
		try {
			oAuthProperties.load(new FileInputStream("src/main/resources/oAuth.properties"));
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Graph.initializeGraphForUserAuth(oAuthProperties, challenge -> System.out.println(challenge.getMessage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testListUsers() throws Exception {
		initializeGraph();
		UserCollectionPage users = Graph.getUsers();
		Assert.assertNotNull(users);
		for (User user : users.getCurrentPage()) {
			Assert.assertNotNull(user);
		}
	}	
}