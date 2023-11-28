package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationpropertiesfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationpropertiesfileApplication.class, args);
    }
}

// The default profile will be active if no other profiles are specified
@Configuration
@Profile("default")
class DefaultProfileConfig {
}
