package com.artificial.intelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.artificial.intelligence")
public class IntelligenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligenceApplication.class, args);
	}

}
