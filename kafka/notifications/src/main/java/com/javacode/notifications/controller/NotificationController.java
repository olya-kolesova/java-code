package com.javacode.notifications.controller;

import com.javacode.notifications.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<Object> sendNotification(@PathVariable long id) {
        notificationService.update(id);
        return new ResponseEntity<>(notificationService.findById(id), HttpStatus.OK);
    }
}
