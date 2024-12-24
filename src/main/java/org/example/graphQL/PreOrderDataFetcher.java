package org.example.graphQL;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.example.DTO.PreOrderDTO;
import org.example.models.PreOrder;
import org.example.models.Product;
import org.example.services.PreOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class PreOrderDataFetcher {

    private final PreOrderService preOrderService;

    @Autowired
    public ModelMapper mapper;

    @Autowired
    public PreOrderDataFetcher(PreOrderService preOrderService) {
        this.preOrderService = preOrderService;
    }

    @DgsQuery
    public PreOrderDTO preOrderById(Long id) {
        return preOrderService.getPreOrderById(id).orElse(null);
    }

    @DgsQuery
    public List<PreOrderDTO> allPreOrders() {
        return preOrderService.getAllPreOrders();
    }



    @DgsMutation
    public Boolean deletePreOrder(Long id) {
        preOrderService.deletePreOrder(id);
        return true;
    }
}
