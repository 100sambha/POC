package com.aithentic;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.admin.reports.Reports;
import com.google.api.services.admin.reports.Reports.CustomerUsageReports;
import com.google.api.services.admin.reports.ReportsScopes;
import com.google.api.services.admin.reports.model.Activities;
import com.google.api.services.admin.reports.model.Activity;
import com.google.api.services.admin.reports.model.UsageReports;
import com.google.api.services.licensing.Licensing;
import com.google.api.services.licensing.LicensingRequest;
import com.google.api.services.licensing.LicensingScopes;
import com.google.api.services.licensing.model.LicenseAssignment;
import com.google.api.services.licensing.model.LicenseAssignmentList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GCPApp {

	public static void main(String[] args) throws GeneralSecurityException, IOException {

		final List<String> SCOPES = Arrays.asList(ReportsScopes.ADMIN_REPORTS_USAGE_READONLY,
				DirectoryScopes.ADMIN_DIRECTORY_USER, LicensingScopes.APPS_LICENSING,ReportsScopes.ADMIN_REPORTS_AUDIT_READONLY);

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
				.setJsonFactory(JSON_FACTORY)
				.setServiceAccountId("adminserviceacc@peak-bit-394212.iam.gserviceaccount.com")
				.setServiceAccountPrivateKeyFromP12File(new File("src/main/resources/com/aithentic/client_p12.p12"))
				.setServiceAccountScopes(SCOPES).setServiceAccountUser("sambhaji.pandhare@aithentic.com").build();

		final String APPLICATION_NAME = "Google Admin SDK Directory API Java Quickstart";
//		Directory service = new Directory.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//				.setApplicationName(APPLICATION_NAME).build();
//		Users result = service
//				.users()
//				.list()
//				.setCustomer("my_customer")
//				.setDomain("aithentic.com")
//				.execute();

		
		Licensing licensingService = new Licensing.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
		
		LicenseAssignmentList licenseAssignments = licensingService.licenseAssignments()
	            .listForProduct("Google-Apps", APPLICATION_NAME)  // Replace with the desired product ID
	            .setCustomerId("C04eyo5vb")   // Replace with the desired customer ID
	            .execute();
		
		
		
//		Reports service =
//				new Reports.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//				.setApplicationName(APPLICATION_NAME)
//				.build();
//		UsageReports s = service.customerUsageReports().get("2023-07-19").setCustomerId("C04eyo5vb").execute();
//		UsageReports s2 = service.userUsageReport().get("113899882381873996077", "2023-08-01").execute();
//		System.out.println(s2.toPrettyString());

		

//		credential.refreshToken();
//		String token = "Bearer " + credential.getAccessToken();
//		System.out.println("Bearer " + token);

//		OkHttpClient client = new OkHttpClient();
//		String URL = "https://admin.googleapis.com/admin/directory/v1/users?customer=my_customer&domain=aithentic.com";
//		Request request = new Request.Builder().url(URL).get().addHeader("Authorization", token )
//				.build();
//		okhttp3.Response response = client.newCall(request).execute();
//		if (response.isSuccessful()) {
//			System.out.println(response.body().string());
//		} else {
//			System.err.println("Error: " + response.code() + " " + response.message());
//			System.out.println(response.body().string());
//		}
//
//		client = new OkHttpClient();
//		URL = "https://admin.googleapis.com/admin/reports/v1/usage/dates/2023-08-01?customerId=C04eyo5vb";
//		request = new Request.Builder().url(URL).get().addHeader("Authorization", token )
//				.build();
//		response = client.newCall(request).execute();
//		if (response.isSuccessful()) {
//			System.out.println(response.body().string());
//		} else {
//			System.err.println("Error: " + response.code() + " " + response.message());
//			System.out.println(response.body().string());
//		}
//
//		client = new OkHttpClient();
//		URL = "https://licensing.googleapis.com/apps/licensing/v1/product/Google-Apps/users?customerId=C04eyo5vb";
//		request = new Request.Builder().url(URL).get().addHeader("Authorization", token )
//				.build();
//		response = client.newCall(request).execute();
//		if (response.isSuccessful()) {
//			System.out.println(response.body().string());
//		} else {
//			System.err.println("Error: " + response.code() + " " + response.message());
//			System.out.println(response.body().string());
//		}
	}
}
