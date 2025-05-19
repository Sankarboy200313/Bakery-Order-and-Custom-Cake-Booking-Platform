package com._7.Bakery.Cake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Server started on port 8080");
		System.out.println("Visit http://localhost:8080/index.html to access the bakery website");
	}

}
