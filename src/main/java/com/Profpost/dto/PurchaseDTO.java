package com.Profpost.dto;

import lombok.Data;

@Data
public class PurchaseDTO {
    private Integer user_id;
    private Integer subscription_id;
    private Integer months;
    private Integer donation_id;
    private Integer id;
    private Float total;
    private String paymentStatus;
    private String createdAt;
    private Integer userId;
    private Integer subscriptionId;
    private Integer donationId;
}
