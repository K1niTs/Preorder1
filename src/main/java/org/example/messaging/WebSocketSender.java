package org.example.messaging;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSender {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendPreOrderUpdateToClients(String preOrderUpdate) {
        messagingTemplate.convertAndSend("/topic/preorder-updates", preOrderUpdate);
    }
}
