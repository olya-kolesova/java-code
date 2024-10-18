package com.javacode.shipping.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.shipping.dto.ShippedOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${topic.name}")
    private String orderTopic;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setDefaultTopic(orderTopic);
    }

    public void sendMessage(ShippedOrderDto shippedOrderDto) throws JsonProcessingException {
        String orderMessage = objectMapper.writeValueAsString(shippedOrderDto);
        logger.info("Sending message to topic {}", orderTopic);
        kafkaTemplate.send(orderTopic, orderMessage);
    }

}
