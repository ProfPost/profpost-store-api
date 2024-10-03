package com.Profpost.mapper;

import com.Profpost.dto.DonationCreateDTO;
import com.Profpost.dto.DonationDetailsDTO;
import com.Profpost.model.entity.Donation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class DonationMapper {
    private final ModelMapper modelMapper;

    public DonationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public DonationDetailsDTO toDetailsDTO(Donation donation) {
        DonationDetailsDTO donationDetailsDTO = modelMapper.map(donation, DonationDetailsDTO.class);
        donationDetailsDTO.setCreatorname(donation.getUser().getName());
        return donationDetailsDTO;
    }

    public Donation toEntity(DonationCreateDTO donationCreateDTO) {
        return modelMapper.map(donationCreateDTO, Donation.class);
    }

    public DonationCreateDTO toEntity(Donation donation) {
        return modelMapper.map(donation, DonationCreateDTO.class);
    }
}
