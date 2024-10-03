package com.Profpost.service;

import com.Profpost.dto.ReactionCreateDTO;
import com.Profpost.dto.ReactionDetailsDTO;

import java.util.List;

public interface ReactionService {
    List<ReactionDetailsDTO> getAll();
    ReactionDetailsDTO Create(ReactionCreateDTO reactionCreateDTO);
    void delete(Integer id);
}
