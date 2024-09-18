package com.Profpost.dto;

import lombok.Data;

@Data
public class SubscriptionResponseDTO {
    private Integer subscriptionId;
    private Integer userId;
    private Integer creatorId;
    private String status;
    private String message;
}
