package com.Profpost.scheduler;

import com.Profpost.model.entity.Subscription;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionExpirationScheduler {
    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateExpiredSubscriptions() {
        List<Subscription> expiredDubscriptions = subscriptionRepository.findAllByEndDateBeforeAndSubscriptionState(
                LocalDateTime.now(), SubscriptionState.SUBSCRIBE
        );
        for (Subscription subscription : expiredDubscriptions) {
            subscription.setSubscriptionState((SubscriptionState.NON_SUBSCRIBE));
            subscriptionRepository.save(subscription);
        }
    }
}
