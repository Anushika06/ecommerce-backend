package com.example.e_commerce_backend.controller;

import com.example.e_commerce_backend.dto.product.CreateProductRequestDto;
import com.example.e_commerce_backend.dto.product.ProductResponseDto;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto createProduct(
            @RequestBody CreateProductRequestDto dto) {

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        Product saved = productService.createProduct(product);

        return ProductResponseDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .price(saved.getPrice())
                .stock(saved.getStock())
                .build();
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String q) {
        return productService.searchProducts(q);
    }
}