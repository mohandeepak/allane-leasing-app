package com.allane.leasing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeasingApp {

	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy(){ return args -> {}; }
	public static void main(String[] args) {
		SpringApplication.run(LeasingApp.class, args);
	}

}
