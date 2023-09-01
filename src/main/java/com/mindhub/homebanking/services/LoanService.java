package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface LoanService {
    public Loan findById(Long id);
    public Set<LoanDTO> getLoans();
    public ResponseEntity<Object> requestLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication);
}
