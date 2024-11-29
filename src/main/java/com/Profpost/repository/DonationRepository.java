package com.Profpost.repository;

import com.Profpost.model.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {

    @Query("SELECT COALESCE(SUM(donation.amount), 0) FROM Donation donation WHERE donation.user.id = :userId AND donation.payment_status = 'PAID'")
    Float getTotalDonationsByUserId(@Param("userId") Integer userId);

    @Query("SELECT purchase.donation, purchase.donation.user, purchase.user.reader FROM Purchase purchase JOIN purchase.donation donation WHERE donation.user.id = :userId AND purchase.payment_status = 'PAID'")
    List<Donation> findDonationsByUserId(@Param("userId") Integer userId);
}
