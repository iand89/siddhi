package com.coding.test.siddhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SiddhiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiddhiApplication.class, args);
	}

}
