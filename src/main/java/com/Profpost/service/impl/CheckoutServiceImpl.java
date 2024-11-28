package com.Profpost.service.impl;

import com.Profpost.dto.PaymentCaptureResponse;
import com.Profpost.dto.PaymentOrderResponse;
import com.Profpost.dto.PurchaseDTO;
import com.Profpost.integration.notification.email.dto.Mail;
import com.Profpost.integration.notification.email.service.EmailService;
import com.Profpost.integration.payment.paypal.dto.OrderCaptureResponse;
import com.Profpost.integration.payment.paypal.dto.OrderResponse;
import com.Profpost.integration.payment.paypal.service.PaypalService;
import com.Profpost.service.CheckoutService;
import com.Profpost.service.PurchaseService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final PaypalService paypalService;
    private final PurchaseService purchaseService;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

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
    public PaymentCaptureResponse capturePayment(String orderId) throws MessagingException {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if(completed) {
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId();
            PurchaseDTO purchaseDTO = purchaseService.confirmPurchase(Integer.parseInt(purchaseIdStr));
            paypalCaptureResponse.setPurchaseId(purchaseDTO.getId());
            sendPurchaseConfirmationEmail(purchaseDTO);
        }

        return paypalCaptureResponse;
    }

    private void sendPurchaseConfirmationEmail(PurchaseDTO purchaseDTO) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Map<String, Object> model = new HashMap<>();
        model.put("user", email);
        model.put("total", purchaseDTO.getTotal());
        //model.put("orderUrl", "https://profpost-web-v1.netlify.app/order/" + purchaseDTO.getId());
        model.put("orderUrl", "http://localhost:4200/order/" + purchaseDTO.getId());

        Mail mail = emailService.createMail(
                email,
                "Confirmación de Pago",
                model,
                mailFrom
        );
        emailService.sendMail(mail, "email/purchase-confirmation-template");
    }
}
