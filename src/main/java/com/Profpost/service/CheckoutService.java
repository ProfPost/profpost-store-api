package com.Profpost.service;

import com.Profpost.dto.PaymentCaptureResponse;
import com.Profpost.dto.PaymentOrderResponse;

public interface CheckoutService {
    PaymentOrderResponse createPayment(Integer purchaseId, String returnUrl, String cancelUrl);
    PaymentCaptureResponse capturePayment(String orderId);
}
