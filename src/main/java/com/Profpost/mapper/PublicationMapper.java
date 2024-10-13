package com.Profpost.mapper;

import com.Profpost.dto.PublicationDTO;
import com.Profpost.model.entity.Publication;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PublicationMapper {
    private final ModelMapper modelMapper;

    public PublicationDTO toDTO(Publication publication) {
        return modelMapper.map(publication, PublicationDTO.class);
    }

    public Publication toEntity(PublicationDTO publicationDTO) {
        return modelMapper.map(publicationDTO, Publication.class);
    }

}
