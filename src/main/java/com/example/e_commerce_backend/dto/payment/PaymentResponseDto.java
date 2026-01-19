package com.example.e_commerce_backend.dto.payment;

import com.example.e_commerce_backend.model.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDto {
    private String paymentId;
    private String orderId;
    private double amount;
    private PaymentStatus status;
}
