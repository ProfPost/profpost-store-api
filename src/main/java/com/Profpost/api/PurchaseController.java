package com.Profpost.api;

import com.Profpost.dto.PurchaseDTO;
import com.Profpost.dto.PurchaseResponseDTO;
import com.Profpost.model.entity.Purchase;
import com.Profpost.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@RequestBody PurchaseDTO purchaseDTO) {
        Purchase purchase = purchaseService.createPurchase(purchaseDTO);

        PurchaseResponseDTO purchaseResponseDTO = new PurchaseResponseDTO();
        purchaseResponseDTO.setId(purchase.getId());
        purchaseResponseDTO.setTotal(purchase.getTotal());
        purchaseResponseDTO.setPaymentStatus(purchase.getPayment_status().name());
        purchaseResponseDTO.setCreatedAt(purchase.getCreated_at().toString());
        purchaseResponseDTO.setUserId(purchase.getUser().getId());
        purchaseResponseDTO.setSubscriptionId(purchase.getSubscription() != null ? purchase.getSubscription().getId() : null);
        purchaseResponseDTO.setDonationId(purchase.getDonation() != null ? purchase.getDonation().getId() : null);

        return ResponseEntity.ok(purchaseResponseDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchaseHistoryByUserId(@PathVariable Integer userId) {
        List<PurchaseResponseDTO> purchaseHistory = purchaseService.getPurchaseHistoryByUserId(userId);
        return ResponseEntity.ok(purchaseHistory);
    }
}
