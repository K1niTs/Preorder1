package org.example.controllers;

import org.example.DTO.PreOrderDTO;
import org.example.services.PreOrderService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/preorders")
public class PreOrderController  {

    private final PreOrderService preOrderService;

    public PreOrderController(PreOrderService preOrderService) {
        this.preOrderService = preOrderService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PreOrderDTO>>> getAllPreOrders() {
        List<PreOrderDTO> preOrders = preOrderService.getAllPreOrders();
        List<EntityModel<PreOrderDTO>> preOrderModels = preOrders.stream()
                .map(preOrder -> {
                    EntityModel<PreOrderDTO> resource = EntityModel.of(preOrder);
                    resource.add(linkTo(methodOn(this.getClass()).getPreOrderById(preOrder.getId())).withSelfRel());
                    resource.add(linkTo(methodOn(this.getClass()).updatePreOrder(preOrder.getId(), preOrder)).withRel("update").withType("PUT").withMedia("application/json"));
                    resource.add(linkTo(methodOn(this.getClass()).deletePreOrder(preOrder.getId())).withRel("delete").withType("DELETE"));
                    return resource;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<PreOrderDTO>> collectionModel = CollectionModel.of(preOrderModels);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PreOrderDTO>> getPreOrderById(@PathVariable Long id) {
        PreOrderDTO preOrder = preOrderService.getPreOrderById(id)
                .orElseThrow(() -> new RuntimeException("PreOrder not found"));

        EntityModel<PreOrderDTO> resource = EntityModel.of(preOrder);
        resource.add(linkTo(methodOn(this.getClass()).getPreOrderById(id)).withSelfRel());
        resource.add(linkTo(methodOn(this.getClass()).updatePreOrder(id, preOrder)).withRel("update").withType("PUT").withMedia("application/json"));
        resource.add(linkTo(methodOn(this.getClass()).deletePreOrder(id)).withRel("delete").withType("DELETE"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<PreOrderDTO>> createPreOrder(@RequestBody PreOrderDTO preOrderDTO) {
        PreOrderDTO createdPreOrder = preOrderService.createPreOrder(preOrderDTO);
        EntityModel<PreOrderDTO> resource = EntityModel.of(createdPreOrder);
        resource.add(linkTo(methodOn(this.getClass()).getPreOrderById(createdPreOrder.getId())).withSelfRel());
        return ResponseEntity.status(201).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PreOrderDTO>> updatePreOrder(@PathVariable Long id, @RequestBody PreOrderDTO preOrderDTO) {
        PreOrderDTO updatedPreOrder = preOrderService.updatePreOrder(id, preOrderDTO);
        if (updatedPreOrder != null) {
            EntityModel<PreOrderDTO> resource = EntityModel.of(updatedPreOrder);
            resource.add(linkTo(methodOn(this.getClass()).getPreOrderById(updatedPreOrder.getId())).withSelfRel());
            resource.add(linkTo(methodOn(this.getClass()).updatePreOrder(id, preOrderDTO)).withRel("update").withType("PUT").withMedia("application/json"));
            resource.add(linkTo(methodOn(this.getClass()).deletePreOrder(id)).withRel("delete").withType("DELETE"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreOrder(@PathVariable Long id) {
        preOrderService.deletePreOrder(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update-status")
    public ResponseEntity<PreOrderDTO> updateStatus(@RequestBody PreOrderDTO preOrderDTO) {
        try {
            PreOrderDTO updatedPreOrder = preOrderService.updateStatusForPreOrder(preOrderDTO);
            return ResponseEntity.ok(updatedPreOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
