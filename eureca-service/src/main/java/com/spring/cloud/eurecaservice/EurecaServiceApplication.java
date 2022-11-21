package com.spring.cloud.eurecaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurecaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurecaServiceApplication.class, args);
	}

}
