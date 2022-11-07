package com.enfint.CreditConveyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan
public class CreditConveyerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditConveyerApplication.class, args);
		System.out.println("Credit Application Started");
	}

}

