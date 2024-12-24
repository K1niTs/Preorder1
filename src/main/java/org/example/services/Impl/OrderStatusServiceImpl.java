package org.example.services.Impl;


import org.example.DTO.OrderStatusDTO;
import org.example.models.OrderStatus;
import org.example.repository.OrderStatusRepository;
import org.example.repository.PreOrderRepository;
import org.example.services.OrderStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;
    private final ModelMapper modelMapper;
    private final PreOrderRepository preOrderRepository;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository, ModelMapper modelMapper, PreOrderRepository preOrderRepository) {
        this.orderStatusRepository = orderStatusRepository;
        this.modelMapper = modelMapper;
        this.preOrderRepository = preOrderRepository;
    }

    @Override
    public OrderStatusDTO createOrderStatus(OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = modelMapper.map(orderStatusDTO, OrderStatus.class);
        OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);
        return modelMapper.map(savedOrderStatus, OrderStatusDTO.class);
    }

    @Override
    public Optional<OrderStatusDTO> getOrderStatusById(Long id) {
        return orderStatusRepository.findById(id)
                .map(orderStatus -> modelMapper.map(orderStatus, OrderStatusDTO.class));
    }

    @Override
    public List<OrderStatusDTO> getAllOrderStatuses() {
        return orderStatusRepository.findAll()
                .stream()
                .map(orderStatus -> modelMapper.map(orderStatus, OrderStatusDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderStatusDTO updateOrderStatus(Long id, OrderStatusDTO updatedOrderStatusDTO) {
        return orderStatusRepository.findById(id)
                .map(existingOrderStatus -> {
                    modelMapper.map(updatedOrderStatusDTO, existingOrderStatus);
                    OrderStatus updatedOrderStatus = orderStatusRepository.save(existingOrderStatus);
                    return modelMapper.map(updatedOrderStatus, OrderStatusDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("OrderStatus with id " + id + " not found"));
    }

    @Override
    public void deleteOrderStatus(Long id) {
        if (orderStatusRepository.existsById(id)) {
            orderStatusRepository.deleteById(id);
        } else {
            throw new RuntimeException("OrderStatus with id " + id + " not found");
        }
    }
}
