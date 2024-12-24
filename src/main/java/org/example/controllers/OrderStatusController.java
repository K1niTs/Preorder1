package org.example.controllers;

import org.example.DTO.OrderStatusDTO;
import org.example.services.OrderStatusService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-statuses")
public class OrderStatusController implements OrderStatusApi {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OrderStatusDTO>>> getAllOrderStatuses() {
        List<OrderStatusDTO> statuses = orderStatusService.getAllOrderStatuses();
        List<EntityModel<OrderStatusDTO>> statusModels = statuses.stream()
                .map(status -> {
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).getOrderStatusById(status.getId())).withSelfRel();
                    Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).updateOrderStatus(status.getId(), status)).withRel("update");
                    Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).deleteOrderStatus(status.getId())).withRel("delete");
                    return EntityModel.of(status, selfLink, updateLink, deleteLink);
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<OrderStatusDTO>> collectionModel = CollectionModel.of(statusModels);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderStatusDTO>> getOrderStatusById(@PathVariable Long id) {
        Optional<OrderStatusDTO> status = orderStatusService.getOrderStatusById(id);
        if (status.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        OrderStatusDTO s = status.get();
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).getOrderStatusById(id)).withSelfRel();
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).updateOrderStatus(id, s)).withRel("update");
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).deleteOrderStatus(id)).withRel("delete");
        return ResponseEntity.ok(EntityModel.of(s, selfLink, updateLink, deleteLink));
    }

    @PostMapping
    public ResponseEntity<EntityModel<OrderStatusDTO>> createOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO) {
        OrderStatusDTO createdStatus = orderStatusService.createOrderStatus(orderStatusDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).getOrderStatusById(createdStatus.getId())).withSelfRel();
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(createdStatus, selfLink));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<OrderStatusDTO>> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusDTO orderStatusDTO) {
        OrderStatusDTO updatedStatus = orderStatusService.updateOrderStatus(id, orderStatusDTO);
        if (updatedStatus != null) {
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).getOrderStatusById(updatedStatus.getId())).withSelfRel();
            Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).updateOrderStatus(id, orderStatusDTO)).withRel("update");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).deleteOrderStatus(id)).withRel("delete");
            return ResponseEntity.ok(EntityModel.of(updatedStatus, selfLink, updateLink, deleteLink));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Long id) {
        Optional<OrderStatusDTO> status = orderStatusService.getOrderStatusById(id);
        if (status.isPresent()) {
            orderStatusService.deleteOrderStatus(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
