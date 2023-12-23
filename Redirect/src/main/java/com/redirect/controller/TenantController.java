package com.redirect.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.redirect.model.GoogleOauthRequest;
import com.redirect.model.GoogleOauthResponse;

@RestController
public class TenantController {
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public TenantController(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}
	
	@GetMapping("/cloud-sync/data-from-gws")
	public void getUsersForGrid(@RequestParam Map<String, String> params) {
		System.out.println("-------------");
		System.out.println(params.get("code"));
		String code = params.get("code");
		String tenantId = params.get("tenant");
		System.out.println("+++++++++++++++"+code);
		System.out.println("---------------"+tenantId);
		System.out.println(params.toString());
	}
	
	
	@GetMapping("/updateTenant")
	public void getUsers(@RequestParam Map<String, String> params) {
		
//		HttpServletResponse response = null;
		String code = params.get("code");
		
		System.out.println(code);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		GoogleOauthRequest req = new GoogleOauthRequest();
		req.setClient_id("176514320904-2i6634nu7smko52gdrqltsakcuu39ljc.apps.googleusercontent.com");
		req.setClient_secret("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");
		req.setCode(code);
		req.setRedirect_uri("http://localhot:8080/updateTenant");
		req.setGrant_type("authorization_code");

		HttpEntity<GoogleOauthRequest> entity = new HttpEntity<GoogleOauthRequest>(req, headers);
		GoogleOauthResponse res = new GoogleOauthResponse();

		res = restTemplate.exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, entity, GoogleOauthResponse.class).getBody();	
		
		
		System.out.println(res);
//		try {
//			response.sendRedirect("https://qa-app.aithentic.com/cloud-sync/data-from-gws?GWSRefreshToken"+res.getRefresh_token());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
	}
}