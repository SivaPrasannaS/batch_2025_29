package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.OrderRequest;
import com.examly.springapp.model.FoodOrder;

public interface FoodOrderService {

    FoodOrder placeOrder(OrderRequest request);

    List<FoodOrder> getAllOrders();

    FoodOrder getOrderById(Long id);

    void deleteOrder(Long id);
}
