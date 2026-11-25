package com.examly.springapp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.DeliveryOrderRequest;
import com.examly.springapp.exception.InvalidStatusTransitionException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.DeliveryOrder;
import com.examly.springapp.repository.DeliveryOrderRepository;
import com.examly.springapp.service.DeliveryOrderService;

@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryOrderServiceImpl.class);
    private static final List<String> LIFECYCLE = List.of("PLACED", "PREPARING", "OUT_FOR_DELIVERY", "DELIVERED");

    private final DeliveryOrderRepository orderRepository;

    public DeliveryOrderServiceImpl(DeliveryOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public DeliveryOrder placeOrder(DeliveryOrderRequest request) {
        DeliveryOrder order = new DeliveryOrder();
        order.setCustomerName(request.getCustomerName());
        order.setRestaurantName(request.getRestaurantName());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus("PLACED");
        DeliveryOrder saved = orderRepository.save(order);
        logger.info("Order {} placed by {} from {}", saved.getId(), saved.getCustomerName(), saved.getRestaurantName());
        return saved;
    }

    @Override
    public DeliveryOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            logger.warn("Order not found with id {}", id);
            return new ResourceNotFoundException("Order not found with id: " + id);
        });
    }

    @Override
    public DeliveryOrder updateStatus(Long id, String newStatus) {
        logger.debug("Validating status change for order {} to {}", id, newStatus);
        DeliveryOrder order = getOrderById(id);
        int current = LIFECYCLE.indexOf(order.getStatus());
        int next = LIFECYCLE.indexOf(newStatus);
        if (next != current + 1) {
            throw new InvalidStatusTransitionException("Cannot change status from " + order.getStatus() + " to " + newStatus);
        }
        order.setStatus(newStatus);
        DeliveryOrder saved = orderRepository.save(order);
        logger.info("Order {} status changed to {}", saved.getId(), saved.getStatus());
        return saved;
    }
}
