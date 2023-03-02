package com.example.customer.services;

import com.example.customer.dto.CustomerDto;
import com.example.customer.entity.CustomerEntity;
import com.example.customer.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService
{
    private final CustomerRepository repository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository repository, ModelMapper modelMapper)
    {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<CustomerDto> getCustomer(final String email)
    {
        Optional<CustomerEntity> entity = this.repository.findCustomerEntityByEmail(email);

        CustomerDto dto = (entity.isPresent()) ?
                modelMapper.map(entity.get(), CustomerDto.class) : new CustomerDto();

        return ResponseEntity.ok(dto);
    }

    @Transactional
    public void remove(final String email)
    {
        this.repository.deleteCustomerEntityByEmail(email);
    }

    public ResponseEntity<CustomerDto> update(final CustomerDto customerDto)
    {
        if (customerDto.getEmail() == null || customerDto.getEmail().isBlank())
            throw new IllegalArgumentException("Customers must have an email address");

        Optional<CustomerEntity> entity = this.repository.findCustomerEntityByEmail(customerDto.getEmail());

        if (entity.isEmpty())
            return this.add(customerDto);

        modelMapper.map(customerDto, entity.get());

        CustomerDto dto = this.modelMapper.map(this.repository.save(entity.get()), CustomerDto.class);
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<CustomerDto> add(final CustomerDto customerDto)
    {
        if (customerDto.getEmail() == null || customerDto.getEmail().isBlank())
            throw new IllegalArgumentException("customer must have an email");

        CustomerEntity entity = this.modelMapper.map(customerDto, CustomerEntity.class);

        this.repository.save(entity);
        return ResponseEntity.ok(customerDto);
    }

    public ResponseEntity<List<CustomerDto>> getAll()
    {
        List<CustomerDto> customerList = StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .map(c -> modelMapper.map(c, CustomerDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerList);
    }
}
