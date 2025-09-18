package com.amstech.std.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.amstech.std.system"})
public class StudentApplication {

	public static void main(String args[])

	{
		SpringApplication.run(StudentApplication.class, args);
		System.out.println("Main Method");
	}
	
}
 // http://localhost:1062/student-api-local/swagger-ui/index.html#/