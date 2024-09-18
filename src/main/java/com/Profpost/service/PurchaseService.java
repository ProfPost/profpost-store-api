package com.Profpost.service;

import com.Profpost.model.entity.Pucharse;
import com.Profpost.model.enums.PaymentStatus;

public interface PurchaseService {
    Pucharse createPurchase(Integer userId, Integer subscriptionId, Integer donationId, float total, PaymentStatus paymentStatus);
}
