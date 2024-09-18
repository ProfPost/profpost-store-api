package com.Profpost.api;

import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionResponseDTO;
import com.Profpost.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> subscribe(@RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionResponseDTO response = subscriptionService.subscribe(subscriptionDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionResponseDTO> unsubscribe(@PathVariable Integer subscriptionId) {
        SubscriptionResponseDTO response = subscriptionService.unsubscribe(subscriptionId);
        return ResponseEntity.ok(response);
    }

}
