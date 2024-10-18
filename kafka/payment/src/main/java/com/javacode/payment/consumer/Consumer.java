package com.javacode.payment.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.payment.dto.OrderDto;
import com.javacode.payment.model.PaidOrder;
import com.javacode.payment.service.PaidOrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Consumer {

    Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final String topic = "new_orders";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ModelMapper modelMapper;
    private final PaidOrderService paidOrderService;

    public Consumer(ModelMapper modelMapper, PaidOrderService paidOrderService) {
        this.modelMapper = modelMapper;
        this.paidOrderService = paidOrderService;
    }

    @KafkaListener(topics = topic, groupId = topic)
    @Transactional
    public void consumeMessage(String message) {
        try {
            logger.info("Consumed message: {}", message);
            OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
            PaidOrder paidOrder = modelMapper.map(orderDto, PaidOrder.class);
            paidOrder.setPaid(true);
            paidOrderService.save(paidOrder);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}
