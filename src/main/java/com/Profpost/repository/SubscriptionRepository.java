package com.Profpost.repository;

import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findByUserAndCreator(User user, User creator);
    List<Subscription> findByCreator(User creator);
}
