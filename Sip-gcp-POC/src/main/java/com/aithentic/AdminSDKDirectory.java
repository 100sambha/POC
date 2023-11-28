package com.aithentic;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminSDKDirectory {

  private static final String APPLICATION_NAME = "Google Admin SDK Directory API Java Quickstart";

  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  public static List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/admin.directory.customer",
		  "https://www.googleapis.com/auth/admin.directory.customer.readonly",
		  "https://www.googleapis.com/auth/admin.directory.device.chromeos",
		  "https://www.googleapis.com/auth/admin.directory.device.chromeos.readonly",
		  "https://www.googleapis.com/auth/admin.directory.device.mobile",
		  "https://www.googleapis.com/auth/admin.directory.device.mobile.action",
		  "https://www.googleapis.com/auth/admin.directory.device.mobile.readonly",
		  "https://www.googleapis.com/auth/admin.directory.domain",
		  "https://www.googleapis.com/auth/admin.directory.domain.readonly",
		  "https://www.googleapis.com/auth/admin.directory.group",
		  "https://www.googleapis.com/auth/admin.directory.group.member",
		  "https://www.googleapis.com/auth/admin.directory.group.member.readonly",
		  "https://www.googleapis.com/auth/admin.directory.group.readonly",
		  "https://www.googleapis.com/auth/admin.directory.orgunit",
		  "https://www.googleapis.com/auth/admin.directory.orgunit.readonly",
		  "https://www.googleapis.com/auth/admin.directory.resource.calendar",
		  "https://www.googleapis.com/auth/admin.directory.resource.calendar.readonly",
		  "https://www.googleapis.com/auth/admin.directory.rolemanagement",
		  "https://www.googleapis.com/auth/admin.directory.rolemanagement.readonly",
		  "https://www.googleapis.com/auth/admin.directory.user",
		  "https://www.googleapis.com/auth/admin.directory.user.alias",
		  "https://www.googleapis.com/auth/admin.directory.user.alias.readonly",
		  "https://www.googleapis.com/auth/admin.directory.user.readonly",
		  "https://www.googleapis.com/auth/admin.directory.user.security",
		  "https://www.googleapis.com/auth/admin.directory.userschema",
		  "https://www.googleapis.com/auth/admin.directory.userschema.readonly",
		  "https://www.googleapis.com/auth/apps.licensing");
  
//  private static final List<String> SCOPES =
//      Collections.singletonList(DirectoryScopes.ADMIN_DIRECTORY_USER_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "client_outh.json";

  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    // Load client secrets.
    InputStream in = AdminSDKDirectory.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    System.out.println(new AuthorizationCodeInstalledApp(flow, receiver).authorize("user").getAccessToken());
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  public static void main(String... args) throws IOException, GeneralSecurityException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Directory service =
        new Directory.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

    Users result = service.users().list()
        .setCustomer("my_customer")
        .setMaxResults(10)
        .setOrderBy("email")
        .execute();
    List<User> users = result.getUsers();
    if (users == null || users.size() == 0) {
      System.out.println("No users found.");
    } else {
      System.out.println("Users:");
      for (User user : users) {
        System.out.println(user.getName().getFullName());
      }
    }
  }
}