package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.model.*;
import com.example.e_commerce_backend.repository.OrderRepository;
import com.example.e_commerce_backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment createPayment(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(order.getTotalAmount())
                .status(PaymentStatus.CREATED)
                .provider("MOCK")
                .build();

        // Update order status
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

    @Override
    public void handlePaymentSuccess(String orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setStatus(PaymentStatus.SUCCESS);
        order.setStatus(OrderStatus.PAID);

        paymentRepository.save(payment);
        orderRepository.save(order);
    }

    @Override
    public void handlePaymentFailure(String orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setStatus(PaymentStatus.FAILED);
        order.setStatus(OrderStatus.FAILED);

        paymentRepository.save(payment);
        orderRepository.save(order);
    }
}

