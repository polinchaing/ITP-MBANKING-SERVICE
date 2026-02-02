package co.istad.polin.account_service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(
			@Value("${service.name}") String name,
			@Value("${secret.weak-password}") String weakPassword,
			@Value("${secret.strong-password}") String strongPassword
	) {
		return args -> {
			System.out.println("service name : " + name);
			System.out.println("weak password : " + weakPassword);
			System.out.println("strong password : " + strongPassword);
		};
	}
}
