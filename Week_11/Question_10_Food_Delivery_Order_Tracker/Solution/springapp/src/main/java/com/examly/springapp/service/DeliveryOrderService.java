package com.examly.springapp.service;

import com.examly.springapp.dto.DeliveryOrderRequest;
import com.examly.springapp.model.DeliveryOrder;

public interface DeliveryOrderService {

    DeliveryOrder placeOrder(DeliveryOrderRequest request);

    DeliveryOrder getOrderById(Long id);

    DeliveryOrder updateStatus(Long id, String newStatus);
}
