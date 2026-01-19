package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.model.Cart;

public interface CartService {

    Cart addToCart(String userId, String productId, int quantity);

    Cart getCartByUserId(String userId);

    void clearCart(String userId);
}
