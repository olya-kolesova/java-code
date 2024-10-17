package com.javacode.orders.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.orders.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Value("${topic.name}")
    private String orderTopic;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setDefaultTopic(orderTopic);
    }

    public void sendMessage(Order order) throws JsonProcessingException {
        String orderMessage = objectMapper.writeValueAsString(order);
        System.out.println(orderMessage);
        kafkaTemplate.send(orderTopic, orderMessage);
    }

}
