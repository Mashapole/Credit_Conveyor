package com.enfint.CreditConveyer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Slf4j
public class CreditConveyerApplication {


	public static void main(String[] args) {
		SpringApplication.run(CreditConveyerApplication.class, args);
		log.info("---------------------------------");
		System.out.println("Credit Application Started");
		log.info("Application Is running");
		log.info("---------------------------------");
	}
}

