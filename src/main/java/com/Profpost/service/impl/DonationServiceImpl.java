package com.Profpost.service.impl;

import com.Profpost.dto.DonationCreateDTO;
import com.Profpost.dto.DonationDetailsDTO;
import com.Profpost.exception.InvalidOperationException;
import com.Profpost.exception.ResourceNotFoundExcept;
import com.Profpost.mapper.DonationMapper;
import com.Profpost.model.entity.Donation;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.PaymentStatus;
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
    private final DonationMapper donationMapper;

    @Transactional
    @Override
    public DonationDetailsDTO createDonation(DonationCreateDTO donationCreateDTO) {
        User user = userRepository.findById(donationCreateDTO.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundExcept("Creador no encontrado con id: " + donationCreateDTO.getCreatorId()));

        if (user.getCreator() == null) {
            throw new InvalidOperationException("Solo los creadores pueden recibir donaciones");
        }

        Donation donation = donationMapper.toEntity(donationCreateDTO);
        donation.setAmount(donationCreateDTO.getAmount());
        donation.setCreated_at(LocalDateTime.now());
        donation.setUser(user);
        donation.setPayment_status(PaymentStatus.PENDING);

        return donationMapper.toDetailsDTO(donationRepository.save(donation));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DonationDetailsDTO> getDonorsAndAmounts(Integer creatorId) {
        List<Donation> donations = donationRepository.findDonationsByUserId(creatorId);


        User user = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundExcept("Creador no encontrado con id: " + creatorId));

        if (user.getCreator() == null) {
            throw new InvalidOperationException("Solo los creadores pueden visualizar su lista de donaciones");
        }

        donations = donations.stream()
                .filter(donation -> donation.getPayment_status() == PaymentStatus.PAID)
                .toList();

        if (donations.isEmpty()) {
            throw new ResourceNotFoundExcept("No existen donaciones para el creador ID: " + creatorId);
        }

        return donations.stream().map(donationMapper::toDetailsDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Float getTotalDonationsByUserId(Integer creatorId) {
        User user = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundExcept("Creador no encontrado con id: " + creatorId));
        if (user.getCreator() == null) {
            throw new InvalidOperationException("Solo los creadores pueden visualizar su total de donaciones");
        }
        return donationRepository.getTotalDonationsByUserId(creatorId);
    }
}
