package com.Profpost.service.impl;

import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionResponseDTO;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.SubscriptionRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.temporal.TemporalAdjusters;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public SubscriptionResponseDTO subscribe(SubscriptionDTO subscriptionDTO){
        User user = userRepository.findById(subscriptionDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription subscription = new Subscription();
        subscription.setStarDate(LocalDateTime.now());
        subscription.setSubscriptionState(SubscriptionState.SUBSCRIBE);

        subscription = subscriptionRepository.save(subscription);

        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setSubscriptionId(subscription.getId());
        response.setUserId(subscriptionDTO.getUser_id());
        response.setCreatorId(subscriptionDTO.getCreator_id());
        response.setStatus("Success");
        response.setMessage("Subscription created successfully!");

        return response;
    }

    @Transactional
    @Override
    public SubscriptionResponseDTO unsubscribe(Integer subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription.setSubscriptionState(SubscriptionState.NON_SUBSCRIBE);
        subscription.setEndDate(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()));

        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setSubscriptionId(subscriptionId);
        response.setStatus("Success");
        response.setMessage("Subscription deleted successfully!");

        return response;
    }
}
