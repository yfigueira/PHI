package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;


public class TestBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(BackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
