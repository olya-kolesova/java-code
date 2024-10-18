package com.javacode.notifications.service;

import com.javacode.notifications.dto.ShippedOrderDto;
import com.javacode.notifications.model.Notification;
import com.javacode.notifications.model.Status;
import com.javacode.notifications.repository.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    public NotificationService(NotificationRepository notificationRepository, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
    }

    public void save(ShippedOrderDto shippedOrderDto) {
        Notification notification = modelMapper.map(shippedOrderDto, Notification.class);
        notification.setStatus(Status.SHIPPED);
        notificationRepository.save(notification);
    }

    @Transactional
    public void update(long id) throws NoSuchElementException {
        Notification notification = findById(id);
        notification.setStatus(Status.DELIVERED);
        notificationRepository.save(notification);
    }

    public Notification findById(long id) throws NoSuchElementException {
        return notificationRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
