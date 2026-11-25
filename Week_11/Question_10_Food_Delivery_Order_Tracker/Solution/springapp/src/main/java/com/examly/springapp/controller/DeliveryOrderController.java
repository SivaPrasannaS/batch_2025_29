package com.examly.springapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.DeliveryOrderRequest;
import com.examly.springapp.model.DeliveryOrder;
import com.examly.springapp.service.DeliveryOrderService;

@RestController
@RequestMapping("/api/orders")
public class DeliveryOrderController {

    private final DeliveryOrderService orderService;

    public DeliveryOrderController(DeliveryOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<DeliveryOrder> placeOrder(@RequestBody DeliveryOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryOrder> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryOrder> updateStatus(@PathVariable Long id, @RequestParam String value) {
        return ResponseEntity.ok(orderService.updateStatus(id, value));
    }
}
