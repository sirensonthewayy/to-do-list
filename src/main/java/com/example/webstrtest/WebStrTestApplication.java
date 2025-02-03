package com.example.webstrtest;

import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;;

@SpringBootApplication
//@EnableJpaRepositories("com.example.webstrtest.repository")
public class WebStrTestApplication {

	@Setter
	private JwtCore jwtCore;

	public static void main(String[] args) {
		SpringApplication.run(WebStrTestApplication.class, args);
	}

}
