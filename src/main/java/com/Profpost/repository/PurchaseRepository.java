package com.Profpost.repository;

import com.Profpost.model.entity.Purchase;
import com.Profpost.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findByUserId(int customerId);
    @Query("SELECT p FROM Purchase p WHERE p.payment_status = :paymentStatus AND p.created_at < :createdAt")
    List<Purchase> findPendingPurchases(@Param("paymentStatus") PaymentStatus paymentStatus, @Param("createdAt") LocalDateTime createdAt);
}
