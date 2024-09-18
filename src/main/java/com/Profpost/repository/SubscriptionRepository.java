package com.Profpost.repository;

import com.Profpost.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
