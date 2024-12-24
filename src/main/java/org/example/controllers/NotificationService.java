package org.example.controllers;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate template;

    public NotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendNotification(String message) {
        System.out.println("Sending notification to WebSocket: " + message);
        template.convertAndSend("/topic/notifications", message);
    }

}
