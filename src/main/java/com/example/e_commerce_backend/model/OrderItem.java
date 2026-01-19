package com.example.e_commerce_backend.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    private String productId;
    private String productName;
    private double price;
    private int quantity;
}