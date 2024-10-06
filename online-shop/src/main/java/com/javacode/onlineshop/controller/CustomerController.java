package com.javacode.onlineshop.controller;

import com.javacode.onlineshop.model.Customer;
import com.javacode.onlineshop.model.Order;
import com.javacode.onlineshop.service.CustomerService;
import com.javacode.onlineshop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping("/api/shop/customer")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @PostMapping("/api/shop/customer/{id}/order")
    public ResponseEntity<Object> makeOrder(@PathVariable long id, @RequestBody Order order) {
        Customer customer = customerService.findById(id);
        order.setCustomer(customer);
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @GetMapping("/api/shop/customer/list")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

}
