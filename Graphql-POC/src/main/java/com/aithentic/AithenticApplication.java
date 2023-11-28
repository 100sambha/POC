package com.aithentic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class AithenticApplication {

	public static void main(String[] args) {
		SpringApplication.run(AithenticApplication.class, args);
	}
	
	@Bean
    public RestTemplate restTemplate() {
       return new RestTemplate();
	}

}