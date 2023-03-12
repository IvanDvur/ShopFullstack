package com.ivand.shopfullstack.service;

import com.ivand.shopfullstack.model.Client;
import com.ivand.shopfullstack.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client resolveClient(Principal principal){
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        return clientRepository.findByUsername(username);
    }
}
