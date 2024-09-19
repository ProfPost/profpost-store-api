package com.Profpost.service.impl;

import com.Profpost.model.entity.Donation;
import com.Profpost.model.entity.User;
import com.Profpost.repository.DonationRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DonationServiceImpl implements DonationService {


    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Donation createdDonation(Integer userid, float amount) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setCreated_at(LocalDateTime.now());
        donation.setUser(user);
        return donationRepository.save(donation);
    }

    @Transactional
    @Override
    public List<Donation> getDonorsAndAmounts(Integer creatorId) {
        return donationRepository.findDonationsByUserId(creatorId);
    }

    @Transactional
    @Override
    public Float getTotalDonationsByUserId(Integer creatorId) {
        return donationRepository.getTotalDonationsByUserId(creatorId);
    }
}
