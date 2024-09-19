package com.Profpost.api;

import com.Profpost.model.entity.Pucharse;
import com.Profpost.model.enums.PaymentStatus;
import com.Profpost.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;


    @PostMapping("/create")
    public ResponseEntity<Pucharse> createPurchase(
            @RequestParam Integer userId,
            @RequestParam(required = false) Integer subscriptionId,
            @RequestParam(required = false) Integer donationId,
            @RequestParam float total,
            @RequestParam PaymentStatus paymentStatus) {

        Pucharse purchase = purchaseService.createPurchase(userId, subscriptionId, donationId, total, paymentStatus);
        return new ResponseEntity<>(purchase, HttpStatus.CREATED);
    }
}
