package com.Profpost.service;

import com.Profpost.model.entity.Reaction;

import java.util.List;

public interface ReactionService {
    Reaction save(Reaction reaction);

    Reaction findById(Integer id);

    void delete(Integer id);

    List<Reaction> findAll();  // Asegúrate de que este método esté declarado

    Reaction createReaction(Reaction reaction);
}
