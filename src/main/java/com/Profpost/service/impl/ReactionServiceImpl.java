package com.Profpost.service.impl;

import com.Profpost.model.entity.Reaction;
import com.Profpost.repository.ReactionRepository;
import com.Profpost.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;

    @Override
    public Reaction save(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    @Override
    public Reaction findById(Integer id) {
        return reactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reaction not found with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        reactionRepository.deleteById(id);
    }

    @Override
    public List<Reaction> findAll() {
        return reactionRepository.findAll();
    }
    @Override
    public Reaction createReaction(Reaction reaction) {
        // Asigna la fecha y hora actual al campo createdAt
        reaction.setCreatedAt(LocalDateTime.now());
        return reactionRepository.save(reaction);
    }

}
