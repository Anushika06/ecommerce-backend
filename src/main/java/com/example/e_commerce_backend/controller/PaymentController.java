package com.example.e_commerce_backend.controller;

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
    public Payment createPayment(@RequestParam String orderId) {
        return paymentService.createPayment(orderId);
    }
}

