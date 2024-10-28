package com.Profpost.service;

import com.Profpost.dto.PurchaseDTO;
import com.Profpost.dto.PurchaseResponseDTO;
import com.Profpost.dto.SubscriptionDTO;
import com.Profpost.model.entity.Purchase;
import java.util.List;

public interface PurchaseService {
    Purchase createPurchase(PurchaseDTO purchaseDTO);
    List<PurchaseResponseDTO> getPurchaseHistoryByUserId(Integer userId);
    PurchaseDTO confirmPurchase(Integer purchaseId);
}
