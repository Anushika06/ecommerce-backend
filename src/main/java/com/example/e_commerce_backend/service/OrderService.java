package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(String userId);

    Order getOrderById(String orderId);

    List<Order> getOrdersByUserId(String userId);
    void cancelOrder(String orderId);

}
