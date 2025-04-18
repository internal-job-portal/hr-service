package com.ukg.hr_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HrServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrServiceApplication.class, args);
	}

}
