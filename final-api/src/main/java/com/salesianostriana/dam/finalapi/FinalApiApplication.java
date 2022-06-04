package com.salesianostriana.dam.finalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalApiApplication.class, args);
	}
}
