package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    List<Product> searchProducts(String query);
}
