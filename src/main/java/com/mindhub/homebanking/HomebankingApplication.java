package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melbamorel@gmail.com");
			Client client2 = new Client("Cristian", "Cruz", "cristianbcm1999@gmail.com");

			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000, client1);
			Account account2 = new Account("VIN002", LocalDateTime.now(), 7500, client1);
			Account account3 = new Account("VIN003", LocalDateTime.now(), 2000, client2);
			Account account4 = new Account("VIN004", LocalDateTime.now(), 4600, client2);

			clientRepository.save(client1);
			clientRepository.save(client2);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			client1.addAcount(account1);
			client1.addAcount(account2);
			client2.addAcount(account3);
			client2.addAcount(account4);

		};
	}
}
