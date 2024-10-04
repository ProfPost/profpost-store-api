package com.Profpost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionReportDTO {
    private Integer quantity;
    private String consultDate;
}
