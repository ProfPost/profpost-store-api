package com.Profpost.api;

import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionReportDTO;
import com.Profpost.dto.SubscriptionResponseDTO;
import com.Profpost.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscription")
@PreAuthorize("hasAnyRole('ADMIN', 'CREATOR')")

public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    @PreAuthorize("hasRole('READER')")
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionResponseDTO response = subscriptionService.subscribe(subscriptionDTO);

        String plainTextResponse = response.getStatus() + ": " + response.getMessage();

        return ResponseEntity.ok(plainTextResponse);
    }

    @DeleteMapping("/{subscriptionId}")
    @PreAuthorize("hasRole('READER')")
    public ResponseEntity<SubscriptionResponseDTO> unsubscribe(@PathVariable Integer subscriptionId) {
        SubscriptionResponseDTO response = subscriptionService.unsubscribe(subscriptionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/report")
    public ResponseEntity<List<SubscriptionReportDTO>> getSubscriptionReport() {
        List<SubscriptionReportDTO> reports = subscriptionService.getSubscriptionReportByDate();
        return ResponseEntity.ok(reports);
    }
}
