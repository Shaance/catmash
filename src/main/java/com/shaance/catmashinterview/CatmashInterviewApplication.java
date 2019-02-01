package com.shaance.catmashinterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class CatmashInterviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatmashInterviewApplication.class, args);
	}

}

