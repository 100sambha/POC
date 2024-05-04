package com.aithentic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.UserCollectionPage;

public class App {
	private static Logger LOGGER = Logger.getLogger(App.class);
	

	public static void main(String[] args) {
		
		LOGGER.info("Java Microsoft Graph API Tutorial");

		final Properties oAuthProperties = new Properties();
		try {
			System.out.println("Current Folder" + System.getProperty("user.dir"));
			oAuthProperties.load(new FileInputStream("src/main/resources/oAuth.properties"));
		} catch (IOException e) {
			LOGGER.error(
					"Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
			return;
		}

		initializeGraph(oAuthProperties);


		Scanner input = new Scanner(System.in);
		int choice = -1;

		while (choice != 0) {
			System.out.println("Please choose one of the following options:");
			System.out.println("0. Exit");
			System.out.println("1. Display access token");
			System.out.println("2. List users (required app-only)");
			try {
				choice = input.nextInt();
			} catch (InputMismatchException ex) {
				LOGGER.warn("Input incorrect format");
			}

			input.nextLine();

			switch (choice) {
			case 0:
				LOGGER.info("Good Bye.....");
				break;
			case 1:
				displayAccessToken();
				break;
			case 2:
				listUsers();
				break;
			default:
				LOGGER.warn("Invalid choice");
			}
		}

		input.close();
	}

	private static void initializeGraph(Properties properties) {
		try {
			Graph.initializeGraphForUserAuth(properties, challenge -> LOGGER.info(challenge.getMessage()));
		} catch (Exception e) {
			LOGGER.error("Error initializing Graph for user auth");
			LOGGER.error(e.getMessage());
		}
	}

	public static String displayAccessToken() {
		try {
			final String accessToken = Graph.getAppOnlyToken();
			LOGGER.info("Access token: " + accessToken);
			return accessToken;
		} catch (Exception e) {
			LOGGER.error("Error getting access token");
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	private static void listUsers() {
		try {
			final UserCollectionPage users = Graph.getUsers();
			for (User user : users.getCurrentPage()) {
				LOGGER.info(user.businessPhones);
				LOGGER.info(user.displayName);
				LOGGER.info(user.givenName);
				LOGGER.info(user.jobTitle);
				LOGGER.info(user.mail);
				LOGGER.info(user.mobilePhone);
				LOGGER.info(user.officeLocation);
				LOGGER.info(user.preferredLanguage);
				LOGGER.info(user.surname);
				LOGGER.info(user.userPrincipalName);
				LOGGER.info(user.id);
			}

		} catch (Exception e) {
			LOGGER.error("Error getting users");
			LOGGER.error(e.getMessage());
			e.printStackTrace();

		}
	}

}