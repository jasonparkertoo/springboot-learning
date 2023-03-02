package com.example.customer.repositories;

import com.example.customer.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>
{
    Optional<CustomerEntity> findCustomerEntityByEmail(String email);
    void deleteCustomerEntityByEmail(String email);
}
