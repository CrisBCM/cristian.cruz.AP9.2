package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @PostMapping("/transactions")
    @Transactional
    public ResponseEntity<Object> transaction(@RequestParam int amount,
                                              @RequestParam String description,
                                              @RequestParam String fromAccountNumber,
                                              @RequestParam String toAccountNumber,
                                              Authentication authentication)
    {
        Client clientOrigen = clientRepository.findByEmail(authentication.getName());
        Account accountOrigen = accountRepository.findByNumber(fromAccountNumber);
        Account accountDestino = accountRepository.findByNumber(toAccountNumber);

        if(amount == 0 || description == null) return new ResponseEntity<>("El monto o la descripcion estan vacios!",HttpStatus.FORBIDDEN);
        if(fromAccountNumber == null || toAccountNumber == null) return new ResponseEntity<>("Numeros de cuenta vacios!", HttpStatus.FORBIDDEN);
        if(fromAccountNumber.equals(toAccountNumber)) return new ResponseEntity<>("Los numeros de cuenta son iguales!", HttpStatus.FORBIDDEN);
        if(!accountRepository.existsByNumber(fromAccountNumber)) return new ResponseEntity<>("La cuenta de origen no existe!", HttpStatus.FORBIDDEN);
        if(!clientOrigen.getAccounts().contains(accountOrigen)) return new ResponseEntity<>("La cuenta de origen no pertenece al Cliente autenticado!", HttpStatus.FORBIDDEN);
        if(!accountRepository.existsByNumber(toAccountNumber)) return  new ResponseEntity<>("La cuenta de destino no existe!", HttpStatus.FORBIDDEN);
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
