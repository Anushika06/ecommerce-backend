package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.CartItem;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.repository.CartRepository;
import com.example.e_commerce_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(String userId, String productId, int quantity) {

        // Validate product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Fetch or create cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder()
                        .userId(userId)
                        .items(new ArrayList<>())
                        .build());

        // Check if product already in cart
        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // increase quantity
            existingItem.get().setQuantity(
                    existingItem.get().getQuantity() + quantity
            );
        } else {
            // add new item
            cart.getItems().add(
                    CartItem.builder()
                            .productId(productId)
                            .quantity(quantity)
                            .build()
            );
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
