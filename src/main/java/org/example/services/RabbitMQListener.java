package org.example.services;


import org.example.controllers.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class RabbitMQListener {

    private final NotificationService notificationService;

    @Autowired
    public RabbitMQListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "orderStatus.requests.queue")
    public void listen(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
        notificationService.sendNotification(message);
    }

}
