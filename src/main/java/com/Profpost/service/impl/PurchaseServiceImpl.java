package com.Profpost.service.impl;

import com.Profpost.model.entity.Pucharse;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import com.Profpost.model.entity.Donation;
import com.Profpost.model.enums.PaymentStatus;
import com.Profpost.repository.PurchaseRepository;
import com.Profpost.repository.SubscriptionRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.repository.DonationRepository;
import com.Profpost.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final DonationRepository donationRepository;

    @Transactional
    @Override
    public Pucharse createPurchase(Integer userId, Integer subscriptionId, Integer donationId, float total, PaymentStatus paymentStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Pucharse purchase = new Pucharse();
        purchase.setUser(user);
        purchase.setTotal(total);
        purchase.setPayment_status(paymentStatus);
        purchase.setCreated_at(LocalDateTime.now());
        purchase.setUpdated_at(LocalDateTime.now());

        if (subscriptionId != null) {
            Subscription subscription = subscriptionRepository.findById(subscriptionId)
                    .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));
            purchase.setSubscription(subscription);
        }

        if (donationId != null) {
            Donation donation = donationRepository.findById(donationId)
                    .orElseThrow(() -> new RuntimeException("Donation not found with id: " + donationId));
            purchase.setDonation(donation);
        }

        return purchaseRepository.save(purchase);  // Guardar la compra en la base de datos
    }
}