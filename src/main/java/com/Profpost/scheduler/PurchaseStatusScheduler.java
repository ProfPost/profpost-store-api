package com.Profpost.scheduler;

import com.Profpost.model.entity.Purchase;
import com.Profpost.model.enums.PaymentStatus;
import com.Profpost.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Component
public class PurchaseStatusScheduler {
    private PurchaseRepository purchaseRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanPendingPurchases() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(24);

        List<Purchase> pendingPurchases = purchaseRepository.findPendingPurchases(PaymentStatus.PENDING, threshold);

        for (Purchase purchase : pendingPurchases) {
            purchase.setPayment_status(PaymentStatus.FAILED);
        }

        purchaseRepository.saveAll(pendingPurchases);
    }
}
