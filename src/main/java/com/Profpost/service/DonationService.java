package com.Profpost.service;

import com.Profpost.model.entity.Donation;

import java.util.List;

public interface DonationService {
    Donation createdDonation(Integer userid , float amount);
    List<Donation> getDonorsAndAmounts(Integer creatorId);
    Float getTotalDonationsByUserId(Integer creatorId);
}
