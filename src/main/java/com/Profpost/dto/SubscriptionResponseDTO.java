package com.Profpost.dto;

import lombok.Data;

@Data
public class SubscriptionResponseDTO {
    private Integer subscriptionId;
    private Integer userId;
    private Integer creatorId;
    private Integer plan_id;
    private String status;
    private String message;
}
