package com.mindhub.homebanking.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionService {
    public ResponseEntity<Object> transaction(int amount,
                                              String description,
                                              String fromAccountNumber,
                                              String toAccountNumber,
                                              Authentication authentication);
}
