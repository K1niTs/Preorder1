package org.example.graphQL;

import org.example.DTO.OrderStatusDTO;
import org.example.DTO.enums.OrderStatusType;
import org.example.services.OrderStatusService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class OrderStatusDataFetcher {

    private final OrderStatusService orderStatusService;

    public OrderStatusDataFetcher(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @DgsQuery
    public List<OrderStatusDTO> allOrderStatuses() {
        return orderStatusService.getAllOrderStatuses()
                .stream()
                .map(orderStatus -> new OrderStatusDTO(orderStatus.getId(), orderStatus.getStatus()))
                .collect(Collectors.toList());
    }

    @DgsQuery
    public OrderStatusDTO orderStatusById(Long id) {
        return orderStatusService.getOrderStatusById(id)
                .map(orderStatus -> new OrderStatusDTO(orderStatus.getId(), orderStatus.getStatus()))
                .orElse(null);
    }

    @DgsMutation
    public OrderStatusDTO createOrderStatus(String status) {
        OrderStatusType orderStatusType = OrderStatusType.valueOf(status.toUpperCase());
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO(null, orderStatusType);
        return orderStatusService.createOrderStatus(orderStatusDTO);
    }

    @DgsMutation
    public OrderStatusDTO updateOrderStatus(Long id, String status) {
        OrderStatusType orderStatusType = OrderStatusType.valueOf(status.toUpperCase());
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO(id, orderStatusType);
        return orderStatusService.updateOrderStatus(id, orderStatusDTO);
    }

    @DgsMutation
    public boolean deleteOrderStatus(Long id) {
        try {
            orderStatusService.deleteOrderStatus(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
