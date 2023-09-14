package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Set;

public interface ClientService {
    public Client getClientByEmail(String email);
    public Client getClientById(Long id);
    public List<ClientDTO> getClients();
    public ClientDTO getClient(Long clientId);
    public Set<AccountDTO> getClientAccounts(String email);
    public Boolean clientAccountsGreaterThan3(String email);
    public void createClient(String firstName, String lastName, String email, String password);

}
