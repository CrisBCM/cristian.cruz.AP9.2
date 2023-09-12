package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

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
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository
			, AccountRepository accountRepository
			, TransactionRepository transactionRepository
			, LoanRepository loanRepository
			, ClientLoanRepository clientLoanRepository
			, CardRepository cardRepository)
	{
		return (args) -> {
//
//			List paymentsHipotercario = List.of(12,24,36,48,60);
//			List paymentsPersonal = List.of(6,12,24);
//			List paymentsAutomotriz = List.of(6,12,24,36);
//
//			Loan loanHipotecario = new Loan("Hipotecario", 500000, paymentsHipotercario);
//			Loan loanPersonal = new Loan("Personal", 100000, paymentsPersonal);
//			Loan loanAutomotriz = new Loan("Automotriz", 300000, paymentsAutomotriz);
//
//			Client client1 = new Client("Melba", "Morel", "melbamorel@gmail.com", passwordEncoder.encode("1234"));
//			Client client2 = new Client("Cristian", "Cruz", "cristianbcm1999@gmail.com",passwordEncoder.encode("1234"));
//
//			Card cardGold = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.DEBIT, CardColor.GOLD, "5463-1264-2543", 543, LocalDate.now(), LocalDate.now().plusYears(5));
//			Card cardTitanium = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.CREDIT, CardColor.TITANIUM, "7765-2214-6654", 142, LocalDate.now(), LocalDate.now().plusYears(5));
//			Card cardSilver = new Card(client2.getFirstName() + " " + client2.getLastName(),CardType.CREDIT, CardColor.SILVER, "8823-6175-7742", 698, LocalDate.now(), LocalDate.now().plusYears(5));
//
//			client1.addCard(cardGold);
//			client1.addCard(cardTitanium);
//			client2.addCard(cardSilver);
//
//			ClientLoan clientLoanMelba1 = new ClientLoan(400000, 60);
//			ClientLoan clientLoanMelba2 = new ClientLoan(50000,12);
//			ClientLoan clientLoanCristian1 = new ClientLoan(100000,24);
//			ClientLoan clientLoanCristian2 = new ClientLoan(200000,36);
//
//			loanHipotecario.addLoan(clientLoanMelba1);
//			loanPersonal.addLoan(clientLoanMelba2);
//			loanPersonal.addLoan(clientLoanCristian1);
//			loanAutomotriz.addLoan(clientLoanCristian2);
//
//			client1.addLoan(clientLoanMelba1);
//			client1.addLoan(clientLoanMelba2);
//			client2.addLoan(clientLoanCristian1);
//			client2.addLoan(clientLoanCristian2);
//
//
//
//			loanRepository.save(loanHipotecario);
//			loanRepository.save(loanPersonal);
//			loanRepository.save(loanAutomotriz);
//
//
//
//			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000);
//			Account account2 = new Account("VIN002", LocalDateTime.now(), 7500);
//			Account account3 = new Account("VIN003", LocalDateTime.now(), 2000);
//			Account account4 = new Account("VIN004", LocalDateTime.now(), 4600);
//
//			client1.addAcount(account1);
//			client1.addAcount(account2);
//			client2.addAcount(account3);
//			client2.addAcount(account4);
//
//			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 5000, "Transaccion de un amigo", LocalDateTime.now());
//			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -2000, "Compra de MousePad", LocalDateTime.now());
//			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 700, "Transaccion a Nicolas", LocalDateTime.now());
//			Transaction transaction4 = new Transaction(TransactionType.DEBIT, -7000, "Compra de teclado", LocalDateTime.now());
//
//			account1.addTransaction(transaction1);
//			account2.addTransaction(transaction2);
//			account3.addTransaction(transaction3);
//			account4.addTransaction(transaction4);
//
//			clientRepository.save(client1);
//			clientRepository.save(client2);
//
//			cardRepository.save(cardGold);
//			cardRepository.save(cardSilver);
//			cardRepository.save(cardTitanium);
//
//			accountRepository.save(account1);
//			accountRepository.save(account2);
//			accountRepository.save(account3);
//			accountRepository.save(account4);
//
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//
//			clientLoanRepository.save(clientLoanMelba1);
//			clientLoanRepository.save(clientLoanMelba2);
//			clientLoanRepository.save(clientLoanCristian1);
//			clientLoanRepository.save(clientLoanCristian2);
		};
	}
}
