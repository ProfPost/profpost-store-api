package com.Profpost.api;

import com.Profpost.model.entity.Donation;
import com.Profpost.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/donation")
public class DonationController {
    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donationRequest) {
        Integer creatorId = donationRequest.getId();
        float amount = donationRequest.getAmount();
        Donation donation = donationService.createdDonation(creatorId, amount);
        return new ResponseEntity<>(donation, HttpStatus.CREATED);
    }

    @GetMapping("/donors/{creatorId}")
    public ResponseEntity<List<Donation>> getDonorsAndAmounts(@PathVariable Integer creatorId) {
        List<Donation> donations = donationService.getDonorsAndAmounts(creatorId);
        if (donations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/total/{creatorId}")
    public ResponseEntity<Float> getDonationAmount(@PathVariable Integer creatorId) {
        Float totalDonations = donationService.getTotalDonationsByUserId(creatorId);
        if (totalDonations == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(totalDonations);
    }
}
