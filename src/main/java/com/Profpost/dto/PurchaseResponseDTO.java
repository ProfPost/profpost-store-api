package com.Profpost.dto;

import lombok.Data;

@Data
public class PurchaseResponseDTO {
    private Integer id;
    private Float total;
    private String paymentStatus;
    private String createdAt;
    private Integer userId;
    private Integer subscriptionId;
    private Integer donationId;
}
