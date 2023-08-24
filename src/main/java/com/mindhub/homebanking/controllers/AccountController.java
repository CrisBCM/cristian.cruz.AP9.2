package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll()
                                .stream()
                                .map(AccountDTO::new)
                                .collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);

        return new AccountDTO(account);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){

        if(clientRepository.findByEmail(authentication.getName()).getAccounts().size() >= 3){

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        }else{

            int randomNumber;
            String numberAccount;

            do {
                randomNumber = (int)Math.floor(Math.random() * (99999999 - 100 + 1) + 100);
                numberAccount = "VIN-" + randomNumber;

            }while(accountRepository.existsByNumber(numberAccount));

            Account newAccount = new Account(numberAccount, LocalDateTime.now(), 0);

            clientRepository.findByEmail(authentication.getName()).addAcount(newAccount);

            accountRepository.save(newAccount);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
