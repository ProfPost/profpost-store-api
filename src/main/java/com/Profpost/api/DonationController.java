package com.Profpost.api;

import com.Profpost.dto.DonationCreateDTO;
import com.Profpost.dto.DonationDetailsDTO;
import com.Profpost.service.DonationService;
import jakarta.validation.Valid;
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
    public ResponseEntity<DonationDetailsDTO> createDonation(@Valid @RequestBody DonationCreateDTO donationCreateDTO) {
        DonationDetailsDTO donationDetailsDTO = donationService.createDonation(donationCreateDTO);
        return new ResponseEntity<>(donationDetailsDTO, HttpStatus.CREATED);
    }

    @GetMapping("/donors/{creatorId}")
    public ResponseEntity<List<DonationDetailsDTO>> getDonorsAndAmounts(@PathVariable Integer creatorId) {
        List<DonationDetailsDTO> donations = donationService.getDonorsAndAmounts(creatorId);
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
