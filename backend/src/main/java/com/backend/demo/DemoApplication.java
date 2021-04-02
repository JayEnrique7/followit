package com.backend.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoApplication {
	private static final Logger logger = LogManager.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		logger.info("Starting the application...");
		SpringApplication.run(DemoApplication.class, args);
	}
}
