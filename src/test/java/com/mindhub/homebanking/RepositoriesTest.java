package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;


    @Test
    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }
    @Test
    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

    }
    @Test
    public void existAccounts(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void existNumberAccount(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,hasItem(hasProperty("number", is("VIN001"))));
    }
    @Test
    public void existCards(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards,is(not(empty())));
    }
    @Test
    public void existCvvCard(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards,hasItem(hasProperty("cvv", is(543))));
    }
    @Test
    public void existClients(){

        List<Client> clients = clientRepository.findAll();

        assertThat(clients,is(not(empty())));
    }
    @Test
    public void existByFirstName(){

        List<Client> clients = clientRepository.findAll();

        assertThat(clients,hasItem(hasProperty("firstName", is("Melba"))));
    }
    @Test
    public void existTransacctions(){

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions,is(not(empty())));
    }
    @Test
    public void existTransacctionType(){

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions,hasItem(hasProperty("type", is(TransactionType.CREDIT))));
    }
}
