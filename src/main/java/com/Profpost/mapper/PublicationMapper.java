package com.Profpost.mapper;

import com.Profpost.dto.PublicationCreateDTO;
import com.Profpost.dto.PublicationDetailsDTO;
import com.Profpost.model.entity.Publication;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class PublicationMapper {
    private final ModelMapper modelMapper;

    public PublicationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public PublicationDetailsDTO toDetailsDTO(Publication publication) {
        PublicationDetailsDTO publicationDetailsDTO = modelMapper.map(publication, PublicationDetailsDTO.class);
        publicationDetailsDTO.setCategoryname(publication.getCategory().getName());
        publicationDetailsDTO.setCreatorname(publication.getCreator().getName());
        return publicationDetailsDTO;
    }

    public Publication toEntity(PublicationCreateDTO publicationCreateDTO) {
        return modelMapper.map(publicationCreateDTO, Publication.class);
    }

    public PublicationCreateDTO publicationCreateDTO(Publication publication) {
        return modelMapper.map(publication, PublicationCreateDTO.class);
    }

}
