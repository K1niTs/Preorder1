package org.example.services;

import org.example.DTO.OrderStatusDTO;

import java.util.List;
import java.util.Optional;

public interface OrderStatusService {
    OrderStatusDTO createOrderStatus(OrderStatusDTO orderStatusDTO);
    Optional<OrderStatusDTO> getOrderStatusById(Long id);
    List<OrderStatusDTO> getAllOrderStatuses();
    OrderStatusDTO updateOrderStatus(Long id, OrderStatusDTO updatedOrderStatusDTO);
    void deleteOrderStatus(Long id);
}
