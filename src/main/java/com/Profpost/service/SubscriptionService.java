package com.Profpost.service;

import com.Profpost.dto.ActiveSubscriptionDTO;
import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.dto.SubscriptionReportDTO;
import com.Profpost.dto.SubscriptionResponseDTO;

import java.util.List;

public interface SubscriptionService {
    SubscriptionResponseDTO subscribe(SubscriptionDTO subscriptionDTO);
    SubscriptionResponseDTO unsubscribe(Integer subscriptionId);
    List<SubscriptionReportDTO> getSubscriptionReportByDate();
    List<ActiveSubscriptionDTO> getActiveSubscriptionByUserId(Integer userId);
    boolean isUserSubscribedToCreator(Integer userId, Integer creatorId);
}
