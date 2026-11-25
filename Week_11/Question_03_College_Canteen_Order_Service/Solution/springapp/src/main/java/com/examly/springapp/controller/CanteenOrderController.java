package com.examly.springapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CanteenOrderRequest;
import com.examly.springapp.model.CanteenOrder;
import com.examly.springapp.service.CanteenOrderService;

@RestController
@RequestMapping("/api/canteen-orders")
public class CanteenOrderController {

    private static final Logger logger = LoggerFactory.getLogger(CanteenOrderController.class);

    private final CanteenOrderService orderService;

    public CanteenOrderController(CanteenOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<CanteenOrder> placeOrder(@RequestBody CanteenOrderRequest request) {
        logger.debug("Received order request from {}", request.getStudentName());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanteenOrder> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/serve")
    public ResponseEntity<CanteenOrder> serveOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.serveOrder(id));
    }
}
