package org.example.services.Impl;

import jakarta.transaction.Transactional;
import org.example.DTO.OrderStatusDTO;
import org.example.DTO.PreOrderDTO;
import org.example.DTO.ProductDTO;
import org.example.models.OrderStatus;
import org.example.models.PreOrder;
import org.example.repository.OrderStatusRepository;
import org.example.repository.PreOrderRepository;
import org.example.services.PreOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreOrderServiceImpl implements PreOrderService {

    private final PreOrderRepository preOrderRepository;
    private final ModelMapper modelMapper;
    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public PreOrderServiceImpl(PreOrderRepository preOrderRepository, ModelMapper modelMapper, OrderStatusRepository orderStatusRepository) {
        this.preOrderRepository = preOrderRepository;
        this.modelMapper = modelMapper;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public PreOrderDTO createPreOrder(PreOrderDTO preOrderDTO) {
        PreOrder preOrder = modelMapper.map(preOrderDTO, PreOrder.class);
        PreOrder savedPreOrder = preOrderRepository.save(preOrder);
        return modelMapper.map(savedPreOrder, PreOrderDTO.class);
    }

    @Override
    public Optional<PreOrderDTO> getPreOrderById(Long id) {
        return preOrderRepository.findById(id)
                .map(preOrder -> modelMapper.map(preOrder, PreOrderDTO.class));
    }

    @Override
    public List<PreOrderDTO> getAllPreOrders() {
        return preOrderRepository.findAll()
                .stream()
                .map(preOrder -> {
                    PreOrderDTO preOrderDTO = modelMapper.map(preOrder, PreOrderDTO.class);

                    if (preOrder.getProduct() != null) {
                        preOrderDTO.setItem(modelMapper.map(preOrder.getProduct(), ProductDTO.class));
                    }

                    if (preOrder.getCustomer() != null) {
                        preOrderDTO.setCustomerName(preOrder.getCustomer().getName());
                    }

                    if (preOrder.getStatus() != null) {
                        preOrderDTO.setStatus(modelMapper.map(preOrder.getStatus(), OrderStatusDTO.class));
                    }

                    return preOrderDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PreOrderDTO updatePreOrder(Long id, PreOrderDTO updatedPreOrderDTO) {
        return preOrderRepository.findById(id)
                .map(existingPreOrder -> {
                    modelMapper.map(updatedPreOrderDTO, existingPreOrder);
                    PreOrder updatedPreOrder = preOrderRepository.save(existingPreOrder);
                    return modelMapper.map(updatedPreOrder, PreOrderDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("PreOrder with id " + id + " not found"));
    }

    @Override
    public void deletePreOrder(Long id) {
        if (preOrderRepository.existsById(id)) {
            preOrderRepository.deleteById(id);
        } else {
            throw new RuntimeException("PreOrder with id " + id + " not found");
        }
    }

    @Override
    @Transactional
    public PreOrderDTO updateStatusForPreOrder(PreOrderDTO preOrderDTO) {
        if (preOrderDTO == null || preOrderDTO.getId() == null) {
            throw new IllegalArgumentException("Предзаказ или его ID не может быть null");
        }

        PreOrder existingPreOrder = preOrderRepository.findById(preOrderDTO.getId())
                .orElseThrow(() -> new RuntimeException("Предзаказ с ID " + preOrderDTO.getId() + " не найден"));

        if (preOrderDTO.getStatus() == null || preOrderDTO.getStatus().getId() == null) {
            throw new IllegalArgumentException("Статус предзаказа или его ID не может быть null");
        }

        Long statusId = preOrderDTO.getStatus().getId();

        OrderStatus newOrderStatus = orderStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Статус с ID " + statusId + " не найден"));

        if (preOrderDTO.getStatus().getStatus() != null) {
            newOrderStatus.setStatus(preOrderDTO.getStatus().getStatus());
            orderStatusRepository.save(newOrderStatus);
        }

        existingPreOrder.setStatus(newOrderStatus);

        PreOrder updatedPreOrder = preOrderRepository.save(existingPreOrder);

        PreOrderDTO resultDTO = modelMapper.map(updatedPreOrder, PreOrderDTO.class);

        if (updatedPreOrder.getCustomer() != null) {
            resultDTO.setCustomerName(updatedPreOrder.getCustomer().getName());
        }
        return resultDTO;
    }
}
