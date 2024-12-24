package org.example.repository;

import org.example.DTO.enums.OrderStatusType;
import org.example.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByStatus(OrderStatusType status);
}