package org.example.messaging;

import org.example.DTO.PreOrderDTO;
import org.example.services.PreOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {

    @Autowired
    private PreOrderService preOrderService;

    @Autowired
    private WebSocketSender webSocketSender;

    @RabbitListener(queues = "orderStatus.update.queue")
    public void receiveOrderStatus(PreOrderDTO preOrderDTO) {
        try {
            System.out.println("Received approval update: " + preOrderDTO);

            preOrderService.updateStatusForPreOrder(preOrderDTO);

            webSocketSender.sendPreOrderUpdateToClients("Status updated: " + preOrderDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
