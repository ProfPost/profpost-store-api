package com.Profpost.mapper;

import com.Profpost.dto.ReactionCreateDTO;
import com.Profpost.dto.ReactionDetailsDTO;
import com.Profpost.model.entity.Reaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ReactionMapper {
    private final ModelMapper modelMapper;

    public ReactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ReactionDetailsDTO toDetailsDTO(Reaction reaction) {
        ReactionDetailsDTO reactionDetailsDTO = modelMapper.map(reaction, ReactionDetailsDTO.class);
        reactionDetailsDTO.setPublicationTitle(reaction.getPublication().getTitle());
        return reactionDetailsDTO;
    }

    public Reaction toEntity(ReactionCreateDTO reactionCreateDTO) {
        return modelMapper.map(reactionCreateDTO, Reaction.class);
    }

    public ReactionCreateDTO reactionCreateDTO(Reaction reaction) {
        return modelMapper.map(reaction, ReactionCreateDTO.class);
    }
}
