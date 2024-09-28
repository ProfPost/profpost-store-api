package com.Profpost.service;

import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionResponseDTO;

public interface SubscriptionService {
    SubscriptionResponseDTO subscribe(SubscriptionDTO subscriptionDTO);
    SubscriptionResponseDTO unsubscribe(Integer subscriptionId);
}
