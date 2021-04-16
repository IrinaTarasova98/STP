package com.apartament.stp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"controllers"})
public class StpApplication {

	public static void main(String[] args) {
		SpringApplication.run(StpApplication.class, args);
		
	}

}


