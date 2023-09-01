package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccount(Long accountId) {
        return new AccountDTO(findById(accountId));
    }

    @Override
    public Account findById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public String getRandomNumberAccount() {

        int randomNumber;
        String numberAccount;

        do {
            randomNumber = (int)Math.floor(Math.random() * (99999999 - 100 + 1) + 100);
            numberAccount = "VIN-" + randomNumber;

        }while(accountRepository.existsByNumber(numberAccount));

        return numberAccount;
    }

    @Override
    public void createClientAccount(String email) {
        Account newAccount = new Account(getRandomNumberAccount(), LocalDateTime.now(), 0);

        clientRepository.findByEmail(email).addAcount(newAccount);

        accountRepository.save(newAccount);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }
}
