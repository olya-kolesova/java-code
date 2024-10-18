package com.javacode.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javacode.payment.dto.PaidOrderDto;
import com.javacode.payment.model.PaidOrder;
import com.javacode.payment.producer.Producer;
import com.javacode.payment.repository.PaidOrderRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaidOrderService {

    Logger logger = LoggerFactory.getLogger(PaidOrderService.class);

    private final PaidOrderRepository paidOrderRepository;
    private final Producer producer;
    private final ModelMapper modelMapper;

    public PaidOrderService(PaidOrderRepository paidOrderRepository, Producer producer, ModelMapper modelMapper) {
        this.paidOrderRepository = paidOrderRepository;
        this.producer = producer;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void save(PaidOrderDto paidOrderDto) throws JsonProcessingException {
        producer.sendMessage(paidOrderDto);
        PaidOrder paidOrder = modelMapper.map(paidOrderDto, PaidOrder.class);
        paidOrderRepository.save(paidOrder);
        logger.info("Paid order saved successfully");
    }

}
