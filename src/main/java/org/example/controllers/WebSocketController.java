package org.example.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.example.models.PreOrder;
import org.example.repository.PreOrderRepository;

@Controller
public class WebSocketController {

    private final PreOrderRepository preOrderRepository;

    public WebSocketController(PreOrderRepository preOrderRepository) {
        this.preOrderRepository = preOrderRepository;
    }

    @MessageMapping("/preorder")
    @SendTo("/topic/preorders")
    public PreOrder sendPreOrder(PreOrder preOrder) throws Exception {
        preOrderRepository.save(preOrder);
        return preOrder;
    }
}
