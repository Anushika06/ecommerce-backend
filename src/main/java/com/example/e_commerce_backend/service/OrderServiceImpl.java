package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.model.*;
import com.example.e_commerce_backend.repository.CartRepository;
import com.example.e_commerce_backend.repository.OrderRepository;
import com.example.e_commerce_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(CartRepository cartRepository,
                            ProductRepository productRepository,
                            OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(String userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem cartItem : cart.getItems()) {

            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Reduce stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            double itemTotal = product.getPrice() * cartItem.getQuantity();
            totalAmount += itemTotal;

            orderItems.add(
                    OrderItem.builder()
                            .productId(product.getId())
                            .productName(product.getName())
                            .price(product.getPrice())
                            .quantity(cartItem.getQuantity())
                            .build()
            );
        }

        Order order = Order.builder()
                .userId(userId)
                .items(orderItems)
                .totalAmount(totalAmount)
                .status(OrderStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);

        // Clear cart after order creation
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
    @Override
    public void cancelOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Cannot cancel a paid order");
        }

        // Restore stock
        for (OrderItem item : order.getItems()) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        // Update order status
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}