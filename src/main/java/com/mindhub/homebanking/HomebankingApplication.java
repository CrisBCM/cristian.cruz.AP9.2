package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melbamorel@gmail.com");
			Client client2 = new Client("Cristian", "Cruz", "cristianbcm1999@gmail.com");

			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000, client1);
			Account account2 = new Account("VIN002", LocalDateTime.now(), 7500, client1);
			Account account3 = new Account("VIN003", LocalDateTime.now(), 2000, client2);
			Account account4 = new Account("VIN004", LocalDateTime.now(), 4600, client2);

			Transaction transaction1 = new Transaction(TransactionType.CREDITO, 5000, "Transaccion de un amigo", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBITO, -2000, "Compra de MousePad", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDITO, 700, "Transaccion a Nicolas", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBITO, -7000, "Compra de teclado", LocalDateTime.now());



			account1.addTransaction(transaction1);
			account2.addTransaction(transaction2);
			account3.addTransaction(transaction3);
			account4.addTransaction(transaction4);

			clientRepository.save(client1);
			clientRepository.save(client2);



			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			client1.addAcount(account1);
			client1.addAcount(account2);
			client2.addAcount(account3);
			client2.addAcount(account4);



		};
	}
}
