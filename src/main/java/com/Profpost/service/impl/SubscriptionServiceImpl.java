package com.Profpost.service.impl;

import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionReportDTO;
import com.Profpost.dto.SubscriptionResponseDTO;
import com.Profpost.exception.InvalidOperationException;
import com.Profpost.model.entity.Plan;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
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
    private final PlanRepository planRepository;

    @Transactional
    @Override
    public SubscriptionResponseDTO subscribe(SubscriptionDTO subscriptionDTO) {
        User user = userRepository.findById(subscriptionDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + subscriptionDTO.getUser_id()));

        User creatorUser = userRepository.findById(subscriptionDTO.getCreator_id())
                .orElseThrow(() -> new RuntimeException("Creador no encontrado con id: " + subscriptionDTO.getCreator_id()));

        if (user.getId().equals(creatorUser.getId())) {
            throw new InvalidOperationException("No puedes suscribirse a tí mismo.");
        }

        if (creatorUser.getCreator() == null) {
            throw new InvalidOperationException("Solo puedes suscribirte a creadores");
        }

        Optional<Subscription> existingActiveSubscription = subscriptionRepository
                .findByUserAndCreatorAndSubscriptionState(user, creatorUser, SubscriptionState.SUBSCRIBE);
        if (existingActiveSubscription.isPresent()) {
            throw new RuntimeException("Ya estás suscrito a este creador.");
        }

        Plan plan = planRepository.findById(subscriptionDTO.getPlan_id())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        int months = subscriptionDTO.getMonths() != null ? subscriptionDTO.getMonths() : 1;
        Subscription subscription = new Subscription();
        subscription.setStarDate(LocalDateTime.now());
        subscription.setEndDate(subscription.getStarDate().plusMonths(months));
        subscription.setSubscriptionState(SubscriptionState.NON_SUBSCRIBE);
        subscription.setUser(user);
        subscription.setCreator(creatorUser);
        subscription.setPlan(plan);

        subscription = subscriptionRepository.save(subscription);

        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setSubscriptionId(subscription.getId());
        response.setUserId(subscriptionDTO.getUser_id());
        response.setPlan_id(subscriptionDTO.getPlan_id());
        response.setCreatorId(subscriptionDTO.getCreator_id());
        response.setStatus("Success");
        response.setMessage("¡Suscripción creada con éxito!");

        return response;
    }

    @Transactional
    @Override
    public SubscriptionResponseDTO unsubscribe(Integer subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (subscription.getSubscriptionState() != SubscriptionState.SUBSCRIBE) {
            throw new InvalidOperationException("La suscripción ya está en estado no suscrito o ha expirado.");
        }

        SubscriptionResponseDTO response = new SubscriptionResponseDTO();
        response.setSubscriptionId(subscriptionId);
        response.setStatus("Success");
        response.setMessage("Se ha cancelado la suscripción con éxito.");

        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubscriptionReportDTO> getSubscriptionReportByDate() {
        List<Object[]>results = subscriptionRepository.getSubscriptionReportByDate();
        List<SubscriptionReportDTO> subscriptionReportDTOS = results.stream()
                .map(result ->
                        new SubscriptionReportDTO(((Integer)result[0]).intValue(),
                                (String)result[1]
                        )
                ).toList();
        return subscriptionReportDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isUserSubscribedToCreator(Integer userId, Integer creatorId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId));

        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creador no encontrado con id: " + creatorId));

        Optional<Subscription> existingSubscription = subscriptionRepository
                .findByUserAndCreatorAndSubscriptionState(user, creator, SubscriptionState.SUBSCRIBE);

        return existingSubscription.isPresent();

    }

}
