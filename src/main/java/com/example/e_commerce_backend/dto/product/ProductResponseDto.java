package com.example.e_commerce_backend.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    private String id;
    private String name;
    private double price;
    private int stock;
}
