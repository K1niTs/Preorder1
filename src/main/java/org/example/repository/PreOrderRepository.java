package org.example.repository;


import org.example.models.OrderStatus;
import org.example.models.PreOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreOrderRepository extends JpaRepository<PreOrder, Long> {
    List<PreOrder> findByProductId(Long productId);
//    Optional<OrderStatus> findByName(String name);
}
