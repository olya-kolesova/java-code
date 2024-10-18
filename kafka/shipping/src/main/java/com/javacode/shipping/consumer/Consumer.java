package com.javacode.shipping.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.shipping.dto.PaidOrderDto;
import com.javacode.shipping.dto.ShippedOrderDto;
import com.javacode.shipping.service.ShippedOrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Consumer {

    Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final String topic = "paid_orders";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ModelMapper modelMapper;
    private final ShippedOrderService shippedOrderService;

    public Consumer(ModelMapper modelMapper, ShippedOrderService shippedOrderService) {
        this.modelMapper = modelMapper;
        this.shippedOrderService = shippedOrderService;
    }

    @KafkaListener(topics = topic, groupId = topic)
    @Transactional
    public void consumeMessage(String message) {
        try {
            logger.info("Consumed message: {}", message);
            PaidOrderDto paidOrderDto = objectMapper.readValue(message, PaidOrderDto.class);
            ShippedOrderDto shippedOrderDto = modelMapper.map(paidOrderDto, ShippedOrderDto.class);
            shippedOrderDto.setPaid(true);
            shippedOrderService.save(shippedOrderDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}
