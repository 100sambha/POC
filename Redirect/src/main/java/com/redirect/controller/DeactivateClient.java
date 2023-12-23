package com.redirect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeactivateClient {

	public static void name() {
		System.out.println("dsjhfbjhsdvjhsb");
	}
	@GetMapping("/scheduler")
	public static String getScheduler(@PathVariable("client_name") String clientName,
			@PathVariable("schedulerGroup") String schedulerGroup) {
		System.out.println("sdbfhdgvbjksdlsdnfkjhbdsfiudbs");
//		SchedulerClient client = null;
//		if (schedulerGroup == null && clientName == null) {
//			return false;
//		}
//		try {
//			client = schedulerConfigurations.client();
//
//			return scheduleService.getScheduler(client, clientName, schedulerGroup,false);
//		} catch (Exception e) {
//			e.printStackTrace();
			return schedulerGroup;
//		}
	}
}
