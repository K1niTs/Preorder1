package org.example.services;


import org.example.DTO.PreOrderDTO;

import java.util.List;
import java.util.Optional;

public interface PreOrderService {
    PreOrderDTO createPreOrder(PreOrderDTO preOrderDTO);
    Optional<PreOrderDTO> getPreOrderById(Long id);
    List<PreOrderDTO> getAllPreOrders();
    PreOrderDTO updatePreOrder(Long id, PreOrderDTO updatedPreOrderDTO);
    void deletePreOrder(Long id);
    PreOrderDTO updateStatusForPreOrder(PreOrderDTO preOrderDTO);

}
