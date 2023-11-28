package com.kafkademo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafkademo.service.Producer;

@RestController
@RequestMapping("/api")
public class KafkaController {
	@Autowired
	private Producer producer;

	@GetMapping("/producermsg")
	public void getMessageFromClient(@RequestParam("msg") String msg) {
		producer.sendMsgToTopic(msg);
	}
}