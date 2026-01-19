package com.example.e_commerce_backend.controller;

import com.example.e_commerce_backend.dto.order.OrderResponseDto;
import com.example.e_commerce_backend.model.Order;
import com.example.e_commerce_backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create order
    @PostMapping
    public OrderResponseDto createOrder(@RequestParam String userId) {

        Order order = orderService.createOrder(userId);

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .items(order.getItems())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    // Bonus: Get orders by user
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable String userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
    }

}
