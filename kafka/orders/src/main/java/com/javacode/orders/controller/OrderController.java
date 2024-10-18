package com.javacode.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javacode.orders.dto.OrderDto;
import com.javacode.orders.model.Order;
import com.javacode.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        return new ResponseEntity<>(orderService.create(orderDto), HttpStatus.CREATED);
    }


}
