package com.aithentic;

import java.util.Arrays;
import java.util.Properties;
import java.util.function.Consumer;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DeviceCodeInfo;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;

import okhttp3.Request;

public class Graph {
	
	private static Properties _properties;
	private static ClientSecretCredential _clientSecretCredential;
	private static GraphServiceClient<Request> _appClient;

	public static void initializeGraphForUserAuth(Properties properties, Consumer<DeviceCodeInfo> challenge)
			throws Exception {
		if (properties == null) {
			throw new Exception("Properties cannot be null");
		}
		_properties = properties;
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
}