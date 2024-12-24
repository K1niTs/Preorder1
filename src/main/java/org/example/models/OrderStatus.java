package org.example.models;

import jakarta.persistence.*;
import org.example.DTO.enums.OrderStatusType;

import java.util.List;

@Entity
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    @OneToMany(mappedBy = "status")
    private List<PreOrder> preOrders;

    public OrderStatus() {}

    public OrderStatus(Long id, OrderStatusType status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OrderStatusType getStatus() { return status; }
    public void setStatus(OrderStatusType status) { this.status = status; }

    public List<PreOrder> getPreOrders() { return preOrders; }
    public void setPreOrders(List<PreOrder> preOrders) { this.preOrders = preOrders; }
}
