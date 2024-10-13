package com.Profpost.service.impl;

import com.Profpost.dto.ReactionCreateDTO;
import com.Profpost.dto.ReactionDetailsDTO;
import com.Profpost.exception.ResourceNotFoundExcept;
import com.Profpost.mapper.ReactionMapper;
import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.Reaction;
import com.Profpost.repository.PublicationRepository;
import com.Profpost.repository.ReactionRepository;
import com.Profpost.service.ReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final PublicationRepository publicationRepository;
    private final ReactionMapper reactionMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ReactionDetailsDTO> getAll() {
        List<Reaction> reactions = reactionRepository.findAll();
        return reactions.stream()
                .map(reactionMapper::toDetailsDTO)
                .toList();
    }

    @Transactional
    @Override
    public ReactionDetailsDTO Create(ReactionCreateDTO reactionCreateDTO) {
        Publication publication = publicationRepository.findById(reactionCreateDTO.getPublicationId())
                .orElseThrow(() -> new ResourceNotFoundExcept("La publicacion no existe con id: " + reactionCreateDTO.getPublicationId()));
        Reaction reaction = reactionMapper.toEntity(reactionCreateDTO);
        reaction.setPublication(publication);
        reaction.setCreatedAt(LocalDateTime.now());
        return reactionMapper.toDetailsDTO(reactionRepository.save(reaction));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Reaction reaction = reactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("La reaccion no existe con id: " + id));
        reactionRepository.delete(reaction);
    }
}
