package com.example.customer.controllers;

import com.example.customer.dto.CustomerDto;
import com.example.customer.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerControllerV1
{
    private final CustomerService service;

    public CustomerControllerV1(CustomerService service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CustomerDto> getCustomer(@RequestParam String email)
    {
        return service.getCustomer(email);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto)
    {
        return this.service.add(customerDto);
    }

    @PutMapping
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto)
    {
        return this.service.update(customerDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@RequestParam String email)
    {
        this.service.remove(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAllCustomers()
    {
        return service.getAll();
    }
}
