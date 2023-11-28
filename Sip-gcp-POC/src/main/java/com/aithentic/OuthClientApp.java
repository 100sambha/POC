package com.aithentic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OuthClientApp {
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/cloud-platform",
			"https://www.googleapis.com/auth/admin.reports.usage.readonly",
			"https://www.googleapis.com/auth/apps.licensing",
			"https://www.googleapis.com/auth/admin.datatransfer",
			"https://www.googleapis.com/auth/admin.directory.user",
			"https://www.googleapis.com/auth/admin.datatransfer.readonly",
			"https://www.googleapis.com/auth/admin.reports.audit.readonly",
			"https://www.googleapis.com/auth/androidpublisher",
			"https://www.googleapis.com/auth/admin.directory.customer",
			"https://apps-apis.google.com/a/feeds/domain");
	private static final String CREDENTIALS_FILE_PATH = "client_outh.json";

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		InputStream in = OuthClientApp.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		return credential;
	}

	public static void main(String... args) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		getCredentials(HTTP_TRANSPORT).refreshToken();
		String accessToken = getCredentials(HTTP_TRANSPORT).getAccessToken();
		String refreshToken = getCredentials(HTTP_TRANSPORT).getRefreshToken();

		System.out.println(accessToken);
		System.out.println(refreshToken);
		
		accessToken = "Bearer " + accessToken;

		OkHttpClient client = new OkHttpClient();
		String url = "https://admin.googleapis.com/admin/reports/v1/usage/dates/2023-08-01?customerId=C04eyo5vb";
		Request request = new Request.Builder().url(url).get().addHeader("Authorization", accessToken).build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			System.out.println(response.body().string());
		} else {
			System.err.println("Error: " + response.code() + " " + response.message());
			System.out.println(response.body().string());
		}
		
		url = "https://admin.googleapis.com/admin/directory/v1/users?customer=my_customer&domain=aithentic.com";
		request = new Request.Builder().url(url).get().addHeader("Authorization", accessToken).build();
		response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			System.out.println(response.body().string());
		} else {
			System.err.println("Error: " + response.code() + " " + response.message());
			System.out.println(response.body().string());
		}
		
		url = "https://licensing.googleapis.com/apps/licensing/v1/product/Google-Apps/users?customerId=C04eyo5vb";
		request = new Request.Builder().url(url).get().addHeader("Authorization", accessToken).build();
		response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			System.out.println(response.body().string());
		} else {
			System.err.println("Error: " + response.code() + " " + response.message());
			System.out.println(response.body().string());
		}
	}
}