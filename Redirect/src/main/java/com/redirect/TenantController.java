package com.redirect;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantController {
	
	@RequestMapping("/cloud-sync/data-from-azure")
	public @ResponseBody void getUsersForGrid(@RequestParam Map<String, String> params) {
		System.out.println(params.get("code"));
		System.out.println(params.toString());
	}
}


//https://qa-app.aithentic.com/cloud-sync/data-from-azure