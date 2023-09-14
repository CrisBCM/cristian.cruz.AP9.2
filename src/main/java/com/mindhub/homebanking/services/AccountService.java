package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    public List<AccountDTO> getAccounts();
    public AccountDTO getAccount(Long accountId);
    public Account findById(Long accountId);
    public String getRandomNumberAccount();
    public void createClientAccount(String email);
    public Account findByNumber(String number);
    public Boolean existsByNumber(String number);
}
