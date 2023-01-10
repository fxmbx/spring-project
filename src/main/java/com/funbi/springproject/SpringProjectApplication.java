package com.funbi.springproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(DemoRepository studentRepository) {
		return args->{
			var student = new DemoEntity("maria","jones","mjones@email.com",22);
			studentRepository.save(student);
		};
	}
}
 