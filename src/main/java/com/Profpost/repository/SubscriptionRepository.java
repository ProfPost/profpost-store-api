package com.Profpost.repository;

import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findByUserAndCreator(User user, User creator);
    List<Subscription> findByCreator(User creator);

    @Query(value = "SELECT * FROM fn_list_subscription_report()", nativeQuery = true)
    List<Object[]> getSubscriptionReportByDate();

    @Query("SELECT s.user FROM Subscription s WHERE s.creator.id = :creatorId AND s.subscriptionState = 'SUBSCRIBE'")
    List<User> findAllSubscribersByCreatorId(Integer creatorId);
}
