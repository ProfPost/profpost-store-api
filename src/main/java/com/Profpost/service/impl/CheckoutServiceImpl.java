package com.Profpost.service.impl;

import com.Profpost.dto.PaymentCaptureResponse;
import com.Profpost.dto.PaymentOrderResponse;
import com.Profpost.dto.PurchaseDTO;
import com.Profpost.integration.payment.paypal.dto.OrderCaptureResponse;
import com.Profpost.integration.payment.paypal.dto.OrderResponse;
import com.Profpost.integration.payment.paypal.service.PaypalService;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.service.CheckoutService;
import com.Profpost.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final PaypalService paypalService;
    private final PurchaseService purchaseService;

    @Override
    public PaymentOrderResponse createPayment(Integer purchaseId, String returnUrl, String cancelUrl) {
        OrderResponse orderResponse = paypalService.createOrder(purchaseId, returnUrl, cancelUrl);

        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaymentOrderResponse(paypalUrl);
    }

    @Override
    public PaymentCaptureResponse capturePayment(String orderId) {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if(completed) {
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId();
            PurchaseDTO purchaseDTO = purchaseService.confirmPurchase(Integer.parseInt(purchaseIdStr));
            paypalCaptureResponse.setPurchaseId(purchaseDTO.getId());
        }

        return paypalCaptureResponse;
    }
}
