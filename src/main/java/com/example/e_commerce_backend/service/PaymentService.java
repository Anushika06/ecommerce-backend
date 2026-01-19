package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.model.Payment;

public interface PaymentService {

    Payment createPayment(String orderId);

    void handlePaymentSuccess(String orderId);

    void handlePaymentFailure(String orderId);
}
