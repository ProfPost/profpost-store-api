package com.Profpost.service.impl;

import com.Profpost.dto.DonationCreateDTO;
import com.Profpost.dto.DonationDetailsDTO;
import com.Profpost.exception.ResourceNotFoundExcept;
import com.Profpost.mapper.DonationMapper;
import com.Profpost.model.entity.Donation;
import com.Profpost.model.entity.User;
import com.Profpost.repository.DonationRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final DonationMapper donationMapper;


    @Transactional
    @Override
    public DonationDetailsDTO createDonation(DonationCreateDTO donationCreateDTO) {
        User user = userRepository.findById(donationCreateDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundExcept("Usuario no encontrado"));

        Donation donation = donationMapper.toEntity(donationCreateDTO);
        donation.setAmount(donationCreateDTO.getAmount());
        donation.setCreated_at(LocalDateTime.now());
        donation.setUser(user);

        return donationMapper.toDetailsDTO(donationRepository.save(donation));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DonationDetailsDTO> getDonorsAndAmounts(Integer creatorId) {
        List<Donation> donations = donationRepository.findDonationsByUserId(creatorId);
        if (donations.isEmpty()) {
            throw new ResourceNotFoundExcept("No donations found for creator ID: " + creatorId);
        }
        return donations.stream().map(donationMapper::toDetailsDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Float getTotalDonationsByUserId(Integer creatorId) {
        return donationRepository.getTotalDonationsByUserId(creatorId);
    }
}
