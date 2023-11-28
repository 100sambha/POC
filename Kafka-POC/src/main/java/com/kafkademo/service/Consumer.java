package com.kafkademo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer
{
	@KafkaListener(topics = "code_decode",groupId = "codeDecode_group")
	public void listenToTopic(String recievedMessage)
	{
		System.out.println("Received Message "+recievedMessage);
	}
}