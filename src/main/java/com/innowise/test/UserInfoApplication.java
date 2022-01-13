package com.innowise.test;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class UserInfoApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserInfoApplication.class, args);
	}
}
