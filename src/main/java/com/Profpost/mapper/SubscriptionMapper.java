package com.Profpost.mapper;

import com.Profpost.dto.ActiveSubscriptionDTO;
import com.Profpost.model.entity.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public ActiveSubscriptionDTO toDTO(Subscription subscription) {
        ActiveSubscriptionDTO dto = new ActiveSubscriptionDTO();
        dto.setCreatorName(subscription.getCreator().getCreator().getName());
        dto.setStartDate(subscription.getStarDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setPlanName(subscription.getPlan().getName());
        dto.setState(subscription.getSubscriptionState());
        return dto;
    }
}
