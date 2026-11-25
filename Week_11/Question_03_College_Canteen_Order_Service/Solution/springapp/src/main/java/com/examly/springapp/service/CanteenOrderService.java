package com.examly.springapp.service;

import com.examly.springapp.dto.CanteenOrderRequest;
import com.examly.springapp.model.CanteenOrder;

public interface CanteenOrderService {

    CanteenOrder placeOrder(CanteenOrderRequest request);

    CanteenOrder getOrderById(Long id);

    CanteenOrder serveOrder(Long id);
}
