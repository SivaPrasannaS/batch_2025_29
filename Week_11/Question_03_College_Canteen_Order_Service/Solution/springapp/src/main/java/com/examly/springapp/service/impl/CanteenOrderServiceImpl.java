package com.examly.springapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.CanteenOrderRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.CanteenOrder;
import com.examly.springapp.repository.CanteenOrderRepository;
import com.examly.springapp.service.CanteenOrderService;

@Service
public class CanteenOrderServiceImpl implements CanteenOrderService {

    private static final Logger logger = LoggerFactory.getLogger(CanteenOrderServiceImpl.class);

    private final CanteenOrderRepository orderRepository;

    public CanteenOrderServiceImpl(CanteenOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public CanteenOrder placeOrder(CanteenOrderRequest request) {
        logger.debug("Calculating total for {} x {} at {}", request.getQuantity(), request.getItemName(),
                request.getUnitPrice());
        CanteenOrder order = new CanteenOrder();
        order.setStudentName(request.getStudentName());
        order.setItemName(request.getItemName());
        order.setQuantity(request.getQuantity());
        order.setUnitPrice(request.getUnitPrice());
        order.setTotalAmount(request.getQuantity() * request.getUnitPrice());
        order.setStatus("PLACED");
        CanteenOrder saved = orderRepository.save(order);
        logger.info("Order {} placed by {} for {}", saved.getId(), saved.getStudentName(), saved.getTotalAmount());
        return saved;
    }

    @Override
    public CanteenOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            logger.warn("Order not found with id {}", id);
            return new ResourceNotFoundException("Order not found with id: " + id);
        });
    }

    @Override
    public CanteenOrder serveOrder(Long id) {
        CanteenOrder order = getOrderById(id);
        order.setStatus("SERVED");
        CanteenOrder saved = orderRepository.save(order);
        logger.info("Order {} marked as SERVED", saved.getId());
        return saved;
    }
}
