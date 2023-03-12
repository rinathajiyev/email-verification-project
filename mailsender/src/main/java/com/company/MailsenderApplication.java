package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class MailsenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailsenderApplication.class, args);
	}
}
