package com.Profpost.service.impl;

import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionReportDTO;
import com.Profpost.dto.SubscriptionResponseDTO;
import com.Profpost.exception.InvalidOperationException;
import com.Profpost.model.entity.Plan;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.ERole;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.*;
import com.Profpost.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final CreatorRepository creatorRepository;
    private final ReaderRepository readerRepository;
    private final PlanRepository planRepository;

    @Transactional
    @Override
    public SubscriptionResponseDTO subscribe(SubscriptionDTO subscriptionDTO){
        User user = userRepository.findById(subscriptionDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + subscriptionDTO.getUser_id()));

        User creator = userRepository.findById(subscriptionDTO.getCreator_id())
                .orElseThrow(() -> new RuntimeException("Creador no encontrado con id: " + subscriptionDTO.getCreator_id()));

        if (!creator.getRole().equals(ERole.CREATOR)) {
            throw new InvalidOperationException("Solo puedes suscribirte a creadores");
        }

        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserAndCreator(user, creator);
        if (existingSubscription.isPresent() && existingSubscription.get().getSubscriptionState() == SubscriptionState.SUBSCRIBE) {
            throw new RuntimeException("You are already subscribed to this creator.");
        }

        Plan plan = planRepository.findById(subscriptionDTO.getPlan_id())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription subscription = new Subscription();
        subscription.setStarDate(LocalDateTime.now());
        subscription.setSubscriptionState(SubscriptionState.SUBSCRIBE);
        subscription.setUser(user);
        subscription.setCreator(creator);
        subscription.setPlan(plan);

        subscription = subscriptionRepository.save(subscription);

        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setSubscriptionId(subscription.getId());
        response.setUserId(subscriptionDTO.getUser_id());
        response.setPlan_id(subscriptionDTO.getPlan_id());
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

    @Transactional(readOnly = true)
    @Override
    public List<SubscriptionReportDTO> getSubscriptionReportByDate() {
        List<Object[]>results = subscriptionRepository.getSubscriptionReportByDate();
        //Mapeo de la lista de objetos a una lista de SubscriptionReportDTO
        List<SubscriptionReportDTO> subscriptionReportDTOS = results.stream()
                .map(result ->
                        new SubscriptionReportDTO(((Integer)result[0]).intValue(),
                                (String)result[1]
                        )
                ).toList();
        return subscriptionReportDTOS;
    }

    @Transactional(readOnly = true)
    public List<User> getSubscribersByCreatorId(Integer creatorId) {
        return subscriptionRepository.findAllSubscribersByCreatorId(creatorId);
    }

}
