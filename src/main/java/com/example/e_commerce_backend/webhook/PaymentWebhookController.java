package com.example.e_commerce_backend.webhook;

import com.example.e_commerce_backend.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/payment")
public class PaymentWebhookController {

    private final PaymentService paymentService;

    public PaymentWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Mock success webhook
    @PostMapping("/success")
    public void paymentSuccess(@RequestParam String orderId) {
        paymentService.handlePaymentSuccess(orderId);
    }

    // Mock failure webhook
    @PostMapping("/failure")
    public void paymentFailure(@RequestParam String orderId) {
        paymentService.handlePaymentFailure(orderId);
    }
}

