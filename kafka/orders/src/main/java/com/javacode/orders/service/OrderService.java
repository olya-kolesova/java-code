package com.javacode.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javacode.orders.dto.OrderDto;
import com.javacode.orders.repository.OrderRepository;
import com.javacode.orders.model.Order;
import com.javacode.orders.producer.Producer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final Producer producer;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, Producer producer, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.producer = producer;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Order create(OrderDto orderDto) throws JsonProcessingException {
        producer.sendMessage(orderDto);
        Order order = modelMapper.map(orderDto, Order.class);
        return orderRepository.save(order);
    }
}
