package com.javacode.onlineshop.controller;

import com.javacode.onlineshop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/shop/order/{id}")
    public ResponseEntity<Object> getOrder(@PathVariable long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }
}
