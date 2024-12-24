package org.example.services.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.DTO.OrderStatusDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreOrderDTO {
    private Long id;
    private int quantity;
    private String customerName;
    private ProductDTO item;
    private OrderStatusDTO status;

    public PreOrderDTO() {}

    public PreOrderDTO(Long id, int quantity, String customerName,ProductDTO item, OrderStatusDTO status) {
        this.id = id;
        this.quantity = quantity;
        this.customerName = customerName;
        this.item = item;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProductDTO getItem() { return item; }
    public void setItem(ProductDTO item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public OrderStatusDTO getStatus() { return status; }
    public void setStatus(OrderStatusDTO status) { this.status = status; }

    @Override
    public String toString() {
        return "PreOrderDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", customerName='" + customerName + '\'' +
                ", item=" + item +
                ", status=" + status +
                '}';
    }
}
