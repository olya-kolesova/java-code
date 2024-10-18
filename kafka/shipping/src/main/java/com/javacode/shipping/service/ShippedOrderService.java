package com.javacode.shipping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javacode.shipping.dto.ShippedOrderDto;
import com.javacode.shipping.model.ShippedOrder;
import com.javacode.shipping.producer.Producer;
import com.javacode.shipping.repository.ShippedOrderRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShippedOrderService {

    Logger logger = LoggerFactory.getLogger(ShippedOrderService.class);

    private final ShippedOrderRepository shippedOrderRepository;
    private final Producer producer;
    private final ModelMapper modelMapper;

    public ShippedOrderService(ShippedOrderRepository shippedOrderRepository, Producer producer, ModelMapper modelMapper) {
        this.shippedOrderRepository = shippedOrderRepository;
        this.producer = producer;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void save(ShippedOrderDto shippedOrderDto) {
        try {
            producer.sendMessage(shippedOrderDto);
            ShippedOrder shippedOrder = modelMapper.map(shippedOrderDto, ShippedOrder.class);
            shippedOrderRepository.save(shippedOrder);
            logger.info("Shipped order saved successfully");
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
