package com.javacode.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javacode.payment.dto.OrderDto;
import com.javacode.payment.model.PaidOrder;
import com.javacode.payment.producer.Producer;
import com.javacode.payment.repository.PaidOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaidOrderService {

    Logger logger = LoggerFactory.getLogger(PaidOrderService.class);

    private final PaidOrderRepository paidOrderRepository;
    private final Producer producer;

    public PaidOrderService(PaidOrderRepository paidOrderRepository, Producer producer) {
        this.paidOrderRepository = paidOrderRepository;
        this.producer = producer;
    }

    @Transactional
    public void save(PaidOrder paidOrder) throws JsonProcessingException {
        producer.sendMessage(paidOrder);
        paidOrderRepository.save(paidOrder);
        logger.info("Paid order saved successfully");
    }

}
