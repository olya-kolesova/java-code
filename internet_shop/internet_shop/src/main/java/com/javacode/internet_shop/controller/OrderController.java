package com.javacode.internet_shop.controller;

import com.javacode.internet_shop.model.Item;
import com.javacode.internet_shop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/api/shop/user/{id}/order")
    public ResponseEntity<Object> buyItems(@PathVariable long id, @RequestBody List<Item> items) {
        return new ResponseEntity<>(orderService.create(id, items), HttpStatus.OK);
    }

    @GetMapping("/api/shop/order/{orderId}")
    public ResponseEntity<Object> getOrder(@PathVariable long orderId) {
        return new ResponseEntity<>(orderService.findById(orderId), HttpStatus.OK);

    }

}
