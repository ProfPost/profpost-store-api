package com.Profpost.dto;

import com.Profpost.model.enums.SubscriptionState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActiveSubscriptionDTO {
    private String creatorName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String planName;
    private SubscriptionState state;
}
