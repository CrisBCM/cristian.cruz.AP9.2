package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long clientId) {
        return new ClientDTO(getClientById(clientId));
    }

    @Override
    public Set<AccountDTO> getClientAccounts(String email) {
        return getClientByEmail(email)
                .getAccounts()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Boolean clientAccountsGreaterThan3(String email) {
        return getClientByEmail(email).getAccounts().size() >= 3;
    }

    @Override
    public void createClient(String firstName, String lastName, String email, String password) {
        Account newAccount = new Account(accountService.getRandomNumberAccount(), LocalDateTime.now(), 0);

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        newClient.addAcount(newAccount);

        clientRepository.save(newClient);
        accountRepository.save(newAccount);
    }
}
