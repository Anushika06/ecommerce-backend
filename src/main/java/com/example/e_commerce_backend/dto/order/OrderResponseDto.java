package com.example.e_commerce_backend.dto.order;

import com.example.e_commerce_backend.model.OrderItem;
import com.example.e_commerce_backend.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    private String orderId;
    private String userId;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderStatus status;
}
