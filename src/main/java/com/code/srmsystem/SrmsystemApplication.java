package com.code.srmsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SrmsystemApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SrmsystemApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SrmsystemApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}

}
