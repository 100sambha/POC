package com.aithentic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.json.CDL;
import org.json.JSONArray;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DeviceCodeInfo;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.callrecords.models.CallRecord;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.SubscribedSku;
import com.microsoft.graph.models.Subscription;
import com.microsoft.graph.models.User;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.LicenseDetailsCollectionPage;
import com.microsoft.graph.requests.MessageCollectionPage;
import com.microsoft.graph.requests.OrganizationCollectionPage;
import com.microsoft.graph.requests.SubscribedSkuCollectionPage;
import com.microsoft.graph.requests.SubscriptionCollectionPage;
import com.microsoft.graph.requests.UserCollectionPage;
import com.microsoft.graph.requests.UserDeltaCollectionPage;

import okhttp3.Request;

public class Graph {
	private static Properties _properties;
	private static GraphServiceClient<Request> _userClient;

	
	private static ClientSecretCredential _clientSecretCredential;
	
	private static GraphServiceClient<Request> _appClient;

	public static void initializeGraphForUserAuth(Properties properties, Consumer<DeviceCodeInfo> challenge)
			throws Exception {
		if (properties == null) {
			throw new Exception("Properties cannot be null");
		}

		_properties = properties;

	}

	public static User getUser() throws Exception {
		if (_userClient == null) {
			throw new Exception("Graph has not been initialized for user auth");
		}

		return _userClient.me().buildRequest().select("displayName,mail,userPrincipalName").get();
	}


	public static String getAppOnlyToken() throws Exception {
		ensureGraphForAppOnlyAuth();
		if (_clientSecretCredential == null) {
			throw new Exception("Graph has not been initialized for app-only auth");
		}

		final String[] graphScopes = new String[] { "https://graph.microsoft.com/.default" };
		
		final TokenRequestContext context = new TokenRequestContext();
		context.addScopes(graphScopes);
		
		final AccessToken token = _clientSecretCredential.getToken(context).block();
		return token.getToken();
	}

	public static MessageCollectionPage getInbox() throws Exception {
		if (_userClient == null) {
			throw new Exception("Graph has not been initialized for user auth");
		}

		return _userClient.me().mailFolders("inbox").messages().buildRequest()
				.select("from,isRead,receivedDateTime,subject").top(25).orderBy("receivedDateTime DESC").get();
	}

	public static void sendMail(String subject, String body, String recipient) throws Exception {
		if (_userClient == null) {
			throw new Exception("Graph has not been initialized for user auth");
		}

		final Message message = new Message();
		message.subject = subject;
		message.body = new ItemBody();
		message.body.content = body;
		message.body.contentType = BodyType.TEXT;

		final Recipient toRecipient = new Recipient();
		toRecipient.emailAddress = new EmailAddress();
		toRecipient.emailAddress.address = recipient;
		message.toRecipients = Arrays.asList(toRecipient);

		_userClient.me().sendMail(UserSendMailParameterSet.newBuilder().withMessage(message).build()).buildRequest()
				.post();
	}

	private static void ensureGraphForAppOnlyAuth() throws Exception {
		if (_properties == null) {
			throw new Exception("Properties cannot be null");
		}

		if (_clientSecretCredential == null) {
			final String clientId = _properties.getProperty("app.clientId");
			final String tenantId = _properties.getProperty("app.tenantId");
			final String clientSecret = _properties.getProperty("app.clientSecret");

			_clientSecretCredential = new ClientSecretCredentialBuilder().clientId(clientId).tenantId(tenantId)
					.clientSecret(clientSecret).clientSecret(clientSecret).build();
		}

		if (_appClient == null) {
			final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
					Arrays.asList("https://graph.microsoft.com/.default"), _clientSecretCredential);

			_appClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();
		}
	}

	public static UserCollectionPage getUsers() throws Exception {
		ensureGraphForAppOnlyAuth();

		return _appClient.users().buildRequest().get();
	}

	public static User getUserByID(String id) throws Exception {
		ensureGraphForAppOnlyAuth();
		User user = _appClient.users(id).buildRequest().get();

		return user;
	}

	public static UserDeltaCollectionPage getDeltaUsers() throws Exception {
		ensureGraphForAppOnlyAuth();
		UserDeltaCollectionPage deltaUser = _appClient.users().delta().buildRequest()
				.select("displayName,jobTitle,mobilePhone").get();
		return deltaUser;
	}

	public static LicenseDetailsCollectionPage listLicenseDetails() throws Exception {
		ensureGraphForAppOnlyAuth();
		LicenseDetailsCollectionPage licenseDetails = _appClient.users("b6113610-63d6-44c3-825d-cf8d56aebc7f")
				.licenseDetails().buildRequest().get();
		return licenseDetails;
	}

	public static SubscribedSkuCollectionPage getSubscribedSkus() throws Exception {
		ensureGraphForAppOnlyAuth();
		return _appClient.subscribedSkus().buildRequest().get();
	}

	public static SubscribedSku getSubscribedSku(String id) throws Exception {
		ensureGraphForAppOnlyAuth();
		SubscribedSku subscribedSku = _appClient.subscribedSkus(id).buildRequest().get();
		return subscribedSku;
	}

	public static JSONArray getOffice365ActivationsUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365ActivationsUserDetail");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365ActivationsUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365ActivationsUserCounts");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365ActiveUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365ActiveUserDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365ActiveUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365ActiveUserCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365ServicesUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365ServicesUserCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getM365AppUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL(
				"https://graph.microsoft.com/v1.0/reports/getM365AppUserDetail(period='D7')?$format=text/csv");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getM365AppUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL(
				"https://graph.microsoft.com/v1.0/reports/getM365AppUserCounts(period='D7')?$format=text/csv");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "text/csv");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		try {
			String csv = new BufferedReader(
					new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8))
					.lines().collect(Collectors.joining("\n"));
			JSONArray json = CDL.toJSONArray(csv);

			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static SubscriptionCollectionPage listSubscriptions() {
		SubscriptionCollectionPage subscriptions = _appClient.subscriptions().buildRequest().get();
		return subscriptions;
	}

	public static Subscription getSubscriptions(String id) {
		Subscription subscription = _appClient.subscriptions(id).buildRequest().get();
		return subscription;
	}

	public static CallRecord getCallRecord(String id) {
		CallRecord callRecord = _appClient.communications().callRecords(id).buildRequest().get();
		return callRecord;
	}

	public static JSONArray getTeamsUserActivityUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getTeamsUserActivityUserDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getTeamsUserActivityCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getTeamsUserActivityCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getTeamsUserActivityUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getTeamsUserActivityUserCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getEmailActivityUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getEmailActivityUserDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getEmailActivityCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getEmailActivityCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getEmailActivityUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getEmailActivityUserCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365GroupsActivityDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365GroupsActivityDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365GroupsActivityCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365GroupsActivityCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365GroupsActivityGroupCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL(
				"https://graph.microsoft.com/v1.0/reports/getOffice365GroupsActivityGroupCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOffice365GroupsActivityStorage() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365GroupsActivityStorage(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		try {
			String csv = new BufferedReader(
					new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8))
					.lines().collect(Collectors.joining("\n"));
			JSONArray json = CDL.toJSONArray(csv);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray getOffice365GroupsActivityFileCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOffice365GroupsActivityFileCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOneDriveActivityUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOneDriveActivityUserDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOneDriveActivityUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOneDriveActivityUserCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOneDriveActivityFileCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOneDriveActivityFileCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOneDriveUsageAccountDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOneDriveUsageAccountDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOneDriveUsageAccountCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOneDriveUsageAccountCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOneDriveUsageFileCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOneDriveUsageFileCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getOneDriveUsageStorage() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getOneDriveUsageStorage(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointActivityUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointActivityUserDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointActivityFileCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointActivityFileCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointActivityUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointActivityUserCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointActivityPages() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointActivityPages(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointSiteUsageDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointSiteUsageDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointSiteUsageFileCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointSiteUsageFileCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointSiteUsageSiteCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointSiteUsageSiteCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointSiteUsageStorage() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointSiteUsageStorage(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getSharePointSiteUsagePages() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getSharePointSiteUsagePages(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getYammerActivityUserDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getYammerActivityUserDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getYammerActivityCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getYammerActivityCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getYammerActivityUserCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getYammerActivityUserCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getYammerGroupsActivityDetail() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getYammerGroupsActivityDetail(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getYammerGroupsActivityGroupCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getYammerGroupsActivityGroupCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static JSONArray getYammerGroupsActivityCounts() throws Exception {
		ensureGraphForAppOnlyAuth();
		URL url = new URL("https://graph.microsoft.com/v1.0/reports/getYammerGroupsActivityCounts(period='D7')");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = getAppOnlyToken();
		conn.setRequestProperty("Accept", "application/octet-stream");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestMethod("GET");
		String csv = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(conn.getInputStream()), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		JSONArray json = CDL.toJSONArray(csv);

		return json;
	}

	public static OrganizationCollectionPage org() throws Exception {
		ensureGraphForAppOnlyAuth();
		OrganizationCollectionPage organization = _appClient.organization()
				.buildRequest()
				.get();
		return organization;
	}
}