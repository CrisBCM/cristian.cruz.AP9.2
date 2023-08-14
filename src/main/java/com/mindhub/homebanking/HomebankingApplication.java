package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository
			, AccountRepository accountRepository
			, TransactionRepository transactionRepository
			, LoanRepository loanRepository
			, ClientLoanRepository clientLoanRepository
			, CardRepository cardRepository)
	{
		return (args) -> {

			List paymentsHipotercario = List.of(12,24,36,48,60);
			List paymentsPersonal = List.of(6,12,24);
			List paymentsAutomotriz = List.of(6,12,24,36);

			Loan loanHipotecario = new Loan("Hipotecario", 500000, paymentsHipotercario);
			Loan loanPersonal = new Loan("Personal", 100000, paymentsPersonal);
			Loan loanAutomotriz = new Loan("Automotriz", 300000, paymentsAutomotriz);

			Client client1 = new Client("Melba", "Morel", "melbamorel@gmail.com");
			Client client2 = new Client("Cristian", "Cruz", "cristianbcm1999@gmail.com");

			Card cardGold = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.DEBIT, CardColor.GOLD, "5463-1264-2543", 543, LocalDate.now(), LocalDate.now().plusYears(5), client1);
			Card cardSilver = new Card(client2.getFirstName() + " " + client2.getLastName(),CardType.CREDIT, CardColor.SILVER, "8823-6175-7742", 698, LocalDate.now(), LocalDate.now().plusYears(5), client2);

			ClientLoan clientLoanMelba1 = new ClientLoan(400000, 60, client1, loanHipotecario);
			ClientLoan clientLoanMelba2 = new ClientLoan(50000,12, client1, loanPersonal);
			ClientLoan clientLoanCristian1 = new ClientLoan(100000,24, client2, loanPersonal);
			ClientLoan clientLoanCristian2 = new ClientLoan(200000,36, client2, loanAutomotriz);

			loanHipotecario.addLoan(clientLoanMelba1);
			loanPersonal.addLoan(clientLoanMelba2);

			client1.addLoan(clientLoanMelba1);
			client1.addLoan(clientLoanMelba2);
			client2.addLoan(clientLoanCristian1);
			client2.addLoan(clientLoanCristian2);

			client1.addCard(cardGold);
			client2.addCard(cardSilver);

			loanRepository.save(loanHipotecario);
			loanRepository.save(loanPersonal);
			loanRepository.save(loanAutomotriz);



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

			cardRepository.save(cardGold);
			cardRepository.save(cardSilver);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			clientLoanRepository.save(clientLoanMelba1);
			clientLoanRepository.save(clientLoanMelba2);

			client1.addAcount(account1);
			client1.addAcount(account2);
			client2.addAcount(account3);
			client2.addAcount(account4);
		};
	}
}
