package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.dto.OrderItemRequest;
import com.examly.springapp.dto.OrderRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.FoodOrder;
import com.examly.springapp.model.OrderItem;
import com.examly.springapp.repository.FoodOrderRepository;
import com.examly.springapp.service.FoodOrderService;

@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    private final FoodOrderRepository orderRepository;

    public FoodOrderServiceImpl(FoodOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public FoodOrder placeOrder(OrderRequest request) {
        FoodOrder order = new FoodOrder();
        order.setCustomerName(request.getCustomerName());
        order.setTableNumber(request.getTableNumber());
        order.setOrderStatus("PLACED");
        double total = 0;
        for (OrderItemRequest itemRequest : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setItemName(itemRequest.getItemName());
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(itemRequest.getUnitPrice());
            order.getItems().add(item);
            total += itemRequest.getQuantity() * itemRequest.getUnitPrice();
        }
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Override
    public List<FoodOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public FoodOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.delete(getOrderById(id));
    }
}
