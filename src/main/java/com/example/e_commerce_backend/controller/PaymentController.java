package com.example.e_commerce_backend.controller;

import com.example.e_commerce_backend.dto.payment.PaymentResponseDto;
import com.example.e_commerce_backend.model.Payment;
import com.example.e_commerce_backend.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Create payment
    @PostMapping
    public PaymentResponseDto createPayment(@RequestParam String orderId) {

        Payment payment = paymentService.createPayment(orderId);

        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .build();
    }
}

