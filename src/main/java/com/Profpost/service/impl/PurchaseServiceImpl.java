package com.Profpost.service.impl;

import com.Profpost.model.entity.Purchase;
import com.Profpost.model.enums.PaymentStatus;
import com.Profpost.repository.PurchaseRepository;
import com.Profpost.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Transactional
    @Override
    public Purchase createPurchase(Purchase purchase) {
        purchase.setCreated_at(LocalDateTime.now());
        purchase.setPayment_status(PaymentStatus.PENDING);

        Float totalSubscription = 0f;
        if (purchase.getSubscription() != null) {
            totalSubscription = purchase.getSubscription().getPlan().getPrice();
        }

        Float totalDonation = 0f;
        if (purchase.getDonation() != null) {
            totalDonation = purchase.getDonation().getAmount();
        }

        Float totalPurchase = totalSubscription + totalDonation;

        purchase.setTotal(totalPurchase);

        return purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> getPurchaseHistoryByUserId(Integer userId) {
        return List.of();
    }
}
