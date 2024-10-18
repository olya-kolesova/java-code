package com.javacode.notifications.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.notifications.dto.ShippedOrderDto;
import com.javacode.notifications.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Notification;

@Component
public class Consumer {

    Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final String topic = "sent_orders";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationService notificationService;

    public Consumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = topic, groupId = topic)
    @Transactional
    public void consumeMessage(String message) {
        try {
            logger.info("Consumed message: {}", message);
            ShippedOrderDto shippedOrderDto = objectMapper.readValue(message, ShippedOrderDto.class);
            notificationService.save(shippedOrderDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}
