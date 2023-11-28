package com.aithentic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAccountApp {
	public static void main(String[] args) {
		List<String> scopes = Arrays.asList("https://www.googleapis.com/auth/cloud-platform",
				"https://www.googleapis.com/auth/admin.reports.audit.readonly",
				"https://www.googleapis.com/auth/admin.reports.usage.readonly",
				"https://www.googleapis.com/auth/admin.datatransfer",
				"https://www.googleapis.com/auth/admin.datatransfer.readonly",
				"https://www.googleapis.com/auth/admin.directory.user",
				"https://www.googleapis.com/auth/apps.licensing",
				"https://www.googleapis.com/auth/androidpublisher",
				"https://www.googleapis.com/auth/admin.directory.group.member",
				"https://www.googleapis.com/auth/admin.directory.group.member.readonly",
				"https://www.googleapis.com/auth/pubsub");

		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials
					.fromStream(new FileInputStream("src/main/resources/com/aithentic/client_service.json"))
					.createScoped(scopes).createDelegated("adminserviceacc@peak-bit-394212.iam.gserviceaccount.com");

			credentials.refresh();
			System.out.println(credentials.getAccessToken().getTokenValue());
			System.out.println(credentials.refreshAccessToken().getTokenValue());

			String refreshAccessToken = "Bearer " + credentials.refreshAccessToken().getTokenValue();
			String accessToken = "Bearer " + credentials.getAccessToken().getTokenValue();

			String url1 = "https://admin.googleapis.com/admin/directory/v1/users?customer=C04eyo5vb&domain=aithentic.com&key=AIzaSyBsv3ArTtUmNe4FzedzDcVbLT9lFBO30oE";
			APICall(url1, accessToken);
			String url2 = "https://admin.googleapis.com/admin/reports/v1/usage/dates/2023-08-13?customerId=C04eyo5vb&maxResults=2&key=AIzaSyBsv3ArTtUmNe4FzedzDcVbLT9lFBO30oE";
			APICall(url2, accessToken);
			String url3 = "https://admin.googleapis.com/admin/reports/v1/usage/users/all/dates/2013-03-03?customerId=C04eyo5vb&key=AIzaSyBsv3ArTtUmNe4FzedzDcVbLT9lFBO30oE";
			APICall(url3, accessToken);
			String url4 = "https://licensing.googleapis.com/apps/licensing/v1/product/Google-Apps/users?customerId=C04eyo5vb&key=AIzaSyBsv3ArTtUmNe4FzedzDcVbLT9lFBO30oE";
			APICall(url4, accessToken);
			String url5 = "https://licensing.googleapis.com/apps/licensing/v1/product/Google-Apps/sku/1010020027/users?customerId=C04eyo5vb&key=AIzaSyBsv3ArTtUmNe4FzedzDcVbLT9lFBO30oE";
			APICall(url5, accessToken);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void APICall(String url, String token) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).get().addHeader("Authorization", token).build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			System.out.println(response.body().string());
		} else {
			System.err.println("Error: " + response.code() + " " + response.message());
			System.out.println(response.body().string());
		}
	}
}