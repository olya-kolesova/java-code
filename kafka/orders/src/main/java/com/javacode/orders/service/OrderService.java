package com.javacode.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javacode.orders.repository.OrderRepository;
import com.javacode.orders.model.Order;
import com.javacode.orders.producer.Producer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final Producer producer;

    public OrderService(OrderRepository orderRepository, Producer producer) {
        this.orderRepository = orderRepository;
        this.producer = producer;
    }

    @Transactional
    public Order create(Order order) throws JsonProcessingException {
        producer.sendMessage(order);
        return orderRepository.save(order);
    }
}
