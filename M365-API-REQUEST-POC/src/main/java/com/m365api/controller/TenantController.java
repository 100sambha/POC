package com.m365api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantController {
//	http://localhost:8080/cloud-sync/data-from-aws
//	@ResponseBody
	@GetMapping("/cloud-sync/data-from-gws")
	public void getUsersForGrid(@RequestParam Map<String, String> params) {
		System.out.println("-------------");
		System.out.println(params.get("code"));
		String code = params.get("code");
		String tenantId = params.get("tenant");
		System.out.println("+++++++++++++++"+code);
		System.out.println("---------------"+tenantId);
		System.out.println(params.toString());
//		return "Done";
		
	}
	
	
	@GetMapping("/code")
	public String getUsers(@RequestParam Map<String, String> params) {
		System.out.println("-------------");
		System.out.println(params.get("code"));
		String code = params.get("code");
		String tenantId = params.get("tenant");
		System.out.println("+++++++++++++++"+code);
		System.out.println("---------------"+tenantId);
		System.out.println(params.toString());
		return "Done";
		
	}
}


//https://qa-app.aithentic.com/cloud-sync/data-from-azure