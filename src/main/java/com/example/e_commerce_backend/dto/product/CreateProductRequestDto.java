package com.example.e_commerce_backend.dto.product;

import lombok.Data;

@Data
public class CreateProductRequestDto {
    private String name;
    private String description;
    private double price;
    private int stock;
}
