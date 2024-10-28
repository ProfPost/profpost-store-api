package com.Profpost.service.impl;

import com.Profpost.dto.PurchaseDTO;
import com.Profpost.dto.PurchaseResponseDTO;
import com.Profpost.model.entity.Donation;
import com.Profpost.model.entity.Purchase;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.PaymentStatus;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.DonationRepository;
import com.Profpost.repository.PurchaseRepository;
import com.Profpost.repository.SubscriptionRepository;
import com.Profpost.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final DonationRepository donationRepository;

    @Transactional
    @Override
    public Purchase createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();

        User user = userRepository.findById(purchaseDTO.getUser_id()).orElseThrow(() -> new RuntimeException("User Not Found"));
        purchase.setUser(user);

        if (purchaseDTO.getSubscription_id() != null) {
            Subscription subscription = subscriptionRepository.findById(purchaseDTO.getSubscription_id()).orElseThrow(() -> new RuntimeException("Subscription Not Found"));
            purchase.setSubscription(subscription);

            purchase.setTotal(subscription.getPlan().getPrice());
        }

        if(purchaseDTO.getDonation_id() != null) {
            Donation donation = donationRepository.findById(purchaseDTO.getDonation_id()).orElseThrow(() -> new RuntimeException("Donation Not Found"));
            purchase.setDonation(donation);

            purchase.setTotal(donation.getAmount());
        }

        purchase.setCreated_at(LocalDateTime.now());
        purchase.setPayment_status(PaymentStatus.PENDING);

        PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
        responseDTO.setId(purchase.getId());
        responseDTO.setTotal(purchase.getTotal());
        responseDTO.setPaymentStatus(purchase.getPayment_status().name());
        responseDTO.setCreatedAt(purchase.getCreated_at().toString());
        responseDTO.setUserId(purchase.getUser().getId());
        responseDTO.setSubscriptionId(purchase.getSubscription() != null ? purchase.getSubscription().getId() : null);
        responseDTO.setDonationId(purchase.getDonation() != null ? purchase.getDonation().getId() : null);

        return purchaseRepository.save(purchase);
    }

    @Override
    public List<PurchaseResponseDTO> getPurchaseHistoryByUserId(Integer userId) {
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);

        return purchases.stream().map(purchase -> {
            PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
            responseDTO.setId(purchase.getId());
            responseDTO.setTotal(purchase.getTotal());
            responseDTO.setPaymentStatus(purchase.getPayment_status().name());
            responseDTO.setCreatedAt(purchase.getCreated_at().toString());
            responseDTO.setUserId(purchase.getUser().getId());
            responseDTO.setSubscriptionId(purchase.getSubscription() != null ? purchase.getSubscription().getId() : null);
            responseDTO.setDonationId(purchase.getDonation() != null ? purchase.getDonation().getId() : null);
            return responseDTO;
        }).toList();
    }

    @Override
    public PurchaseDTO confirmPurchase(Integer purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new RuntimeException("Purchase Not Found"));
        purchase.setPayment_status(PaymentStatus.PAID);

        Purchase updatedPurchase = purchaseRepository.save(purchase);

        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(updatedPurchase.getId());
        purchaseDTO.setTotal(updatedPurchase.getTotal());
        purchaseDTO.setPaymentStatus(updatedPurchase.getPayment_status().name());
        purchaseDTO.setCreatedAt(updatedPurchase.getCreated_at().toString());
        purchaseDTO.setUserId(updatedPurchase.getUser().getId());
        purchaseDTO.setSubscriptionId(updatedPurchase.getSubscription() != null ? updatedPurchase.getSubscription().getId() : null);
        purchaseDTO.setDonationId(updatedPurchase.getDonation() != null ? updatedPurchase.getDonation().getId() : null);

        if (updatedPurchase.getSubscription() != null) {
            Subscription subscription = updatedPurchase.getSubscription();
            subscription.setSubscriptionState(SubscriptionState.SUBSCRIBE);
            subscription.setStarDate(LocalDateTime.now());

            if (subscription.getPlan() == null) {
                throw new RuntimeException("Plan is required for the subscription");
            }

            subscriptionRepository.save(subscription);
        }
        if (updatedPurchase.getDonation() != null) {
            Donation donation = updatedPurchase.getDonation();
            donation.setPayment_status(PaymentStatus.PAID);
            donationRepository.save(donation);
        }

        return purchaseDTO;
    }
}
