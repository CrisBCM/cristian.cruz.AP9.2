package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> transaction(int amount, String description, String fromAccountNumber, String toAccountNumber, Authentication authentication) {
        Client clientOrigen = clientService.getClientByEmail(authentication.getName());
        Account accountOrigen = accountService.findByNumber(fromAccountNumber);
        Account accountDestino = accountService.findByNumber(toAccountNumber);

        if(amount == 0 || description == null) return new ResponseEntity<>("El monto o la descripcion estan vacios!", HttpStatus.FORBIDDEN);
        if(fromAccountNumber == null || toAccountNumber == null) return new ResponseEntity<>("Numeros de cuenta vacios!", HttpStatus.FORBIDDEN);
        if(fromAccountNumber.equals(toAccountNumber)) return new ResponseEntity<>("Los numeros de cuenta son iguales!", HttpStatus.FORBIDDEN);
        if(!accountService.existsByNumber(fromAccountNumber)) return new ResponseEntity<>("La cuenta de origen no existe!", HttpStatus.FORBIDDEN);
        if(!clientOrigen.getAccounts().contains(accountOrigen)) return new ResponseEntity<>("La cuenta de origen no pertenece al Cliente autenticado!", HttpStatus.FORBIDDEN);
        if(!accountService.existsByNumber(toAccountNumber)) return  new ResponseEntity<>("La cuenta de destino no existe!", HttpStatus.FORBIDDEN);
        if(!(accountOrigen.getBalance() >= amount)) return new ResponseEntity<>("Monto insuficiente!", HttpStatus.FORBIDDEN);

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, accountOrigen.getNumber().concat(" " + description), LocalDateTime.now());
        accountOrigen.addTransaction(debitTransaction);
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, accountDestino.getNumber().concat(" " + description), LocalDateTime.now());
        accountDestino.addTransaction(creditTransaction);

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        accountOrigen.setBalance(accountOrigen.getBalance() - amount);
        accountDestino.setBalance(accountDestino.getBalance() + amount);

        accountRepository.save(accountOrigen);
        accountRepository.save(accountDestino);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
