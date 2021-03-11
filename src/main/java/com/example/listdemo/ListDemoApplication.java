package com.example.listdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ListDemoApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ListDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ListDemoApplication.class, args);
	}

	@PostConstruct
	public void init() {
		LOGGER.error("CPU: {}", Runtime.getRuntime().availableProcessors());
	}
}
