package com.Profpost.repository;

import com.Profpost.model.entity.Creator;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.SubscriptionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    @Query(value = "SELECT * FROM fn_list_subscription_report()", nativeQuery = true)
    List<Object[]> getSubscriptionReportByDate();

    List<Subscription> findAllByEndDateBeforeAndSubscriptionState(LocalDateTime endDate, SubscriptionState state);
    Optional<Subscription> findByUserAndCreatorAndSubscriptionState(User user, User creator, SubscriptionState subscriptionState);
    List<Subscription> findByCreatorAndSubscriptionState(Creator creator, SubscriptionState subscriptionState);
    List<Subscription> findAllByUserIdAndSubscriptionState(Integer userId, SubscriptionState state);
}
