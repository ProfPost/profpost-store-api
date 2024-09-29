package com.Profpost.repository;

import com.Profpost.model.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Integer> {

    @Query("SELECT COALESCE(SUM(donation.amount), 0) FROM Donation donation WHERE donation.user.id = :userId")
    Float getTotalDonationsByUserId(@Param("userId") Integer userId);

    @Query("SELECT donation FROM Donation donation WHERE donation.user.id = :userId")
    List<Donation> findDonationsByUserId(@Param("userId") Integer userId);
}
