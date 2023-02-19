package com.ivand.shopfullstack.repository;

import com.ivand.shopfullstack.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long> {
    Client findByUsername(String username);
}
