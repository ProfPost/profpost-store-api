package com.Profpost.service;

import com.Profpost.dto.DonationCreateDTO;
import com.Profpost.dto.DonationDetailsDTO;

import java.util.List;

public interface DonationService {
    DonationDetailsDTO createDonation( DonationCreateDTO donationCreateDTO);
    List<DonationDetailsDTO> getDonorsAndAmounts(Integer creatorId);
    Float getTotalDonationsByUserId(Integer creatorId);
}
