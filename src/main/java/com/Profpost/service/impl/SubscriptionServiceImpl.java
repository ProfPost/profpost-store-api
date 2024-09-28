package com.Profpost.service.impl;

import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionResponseDTO;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.Role;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.SubscriptionRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.temporal.TemporalAdjusters;

import java.time.LocalDateTime;
import java.util.Optional;

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

        if (user.getRole() != Role.READER) {
            throw new RuntimeException("Only users with role READER can subscribe");
        }

        User creator = userRepository.findById(subscriptionDTO.getCreator_id())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        if (creator.getRole() != Role.CREATOR) {
            throw new RuntimeException("You can only subscribe to users with role CREATOR");
        }

        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserAndCreator(user, creator);
        if (existingSubscription.isPresent() && existingSubscription.get().getSubscriptionState() == SubscriptionState.SUBSCRIBE) {
            throw new RuntimeException("You are already subscribed to this creator.");
        }

        Subscription subscription = new Subscription();
        subscription.setStarDate(LocalDateTime.now());
        subscription.setSubscriptionState(SubscriptionState.SUBSCRIBE);
        subscription.setUser(user);
        subscription.setCreator(creator);

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

        LocalDateTime endDate = subscription.getStarDate().plusDays(30);
        subscription.setEndDate(endDate);
        subscription.setSubscriptionState(SubscriptionState.NON_SUBSCRIBE);

        subscriptionRepository.save(subscription);

        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setSubscriptionId(subscriptionId);
        response.setStatus("Success");
        response.setMessage("Subscription will end after 30 days!");

        return response;
    }
}
