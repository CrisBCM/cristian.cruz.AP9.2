package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public Set<LoanDTO> getLoans(){
        return loanRepository
                .findAll()
                .stream()
                .map(LoanDTO::new)
                .collect(Collectors.toSet());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> requestLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Account account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        Client client = clientRepository.findByEmail(authentication.getName());

        if (loanApplicationDTO.getLoanId() == 0  ) return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getAmount() == 0) return new ResponseEntity<>("El monto no puede ser 0", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getPayments() == 0) return new ResponseEntity<>("Las cuotas no pueden ser 0", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getToAccountNumber() == null) return new ResponseEntity<>("Numero de cuenta nulo", HttpStatus.FORBIDDEN);
        if (!loanRepository.existsById(loanApplicationDTO.getLoanId())) return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) return new ResponseEntity<>("El monto pedido supera el limite", HttpStatus.FORBIDDEN);
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) return new ResponseEntity<>("Cantidad de cuotas no disponible", HttpStatus.FORBIDDEN);
        if (!accountRepository.existsByNumber(loanApplicationDTO.getToAccountNumber())) return new ResponseEntity<>("Cuenta inexistente", HttpStatus.FORBIDDEN);
        if (!client.getAccounts().contains(account))
            return  new ResponseEntity<>("El numero de cuenta no le pertenece al usuario autenticado", HttpStatus.FORBIDDEN);

        int percentage20Amount = loanApplicationDTO.getAmount() * 20 / 100;
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() + percentage20Amount, loanApplicationDTO.getPayments());
        loan.addLoan(clientLoan);
        client.addLoan(clientLoan);


        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loan.getName() + " loan approved", LocalDateTime.now());

        account.addTransaction(transaction);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());

        loanRepository.save(loan);
        clientRepository.save(client);
        clientLoanRepository.save(clientLoan);
        accountRepository.save(account);
        transactionRepository.save(transaction);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
