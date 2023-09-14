package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Set<LoanDTO> getLoans() {
        return loanRepository
                .findAll()
                .stream()
                .map(LoanDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<Object> requestLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Loan loan = findById(loanApplicationDTO.getLoanId());
        Account account = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
        Client client = clientService.getClientByEmail(authentication.getName());

        if (loanApplicationDTO.getLoanId() == 0  ) return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getAmount() == 0) return new ResponseEntity<>("El monto no puede ser 0", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getPayments() == 0) return new ResponseEntity<>("Las cuotas no pueden ser 0", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getToAccountNumber() == null) return new ResponseEntity<>("Numero de cuenta nulo", HttpStatus.FORBIDDEN);
        if (!loanRepository.existsById(loanApplicationDTO.getLoanId())) return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) return new ResponseEntity<>("El monto pedido supera el limite", HttpStatus.FORBIDDEN);
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) return new ResponseEntity<>("Cantidad de cuotas no disponible", HttpStatus.FORBIDDEN);
        if (!accountService.existsByNumber(loanApplicationDTO.getToAccountNumber())) return new ResponseEntity<>("Cuenta inexistente", HttpStatus.FORBIDDEN);
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
