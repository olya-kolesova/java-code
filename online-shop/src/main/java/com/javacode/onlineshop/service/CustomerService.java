package com.javacode.onlineshop.service;

import com.javacode.onlineshop.model.Customer;
import com.javacode.onlineshop.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) throws NoSuchElementException {
        return customerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
