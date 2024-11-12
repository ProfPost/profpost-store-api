package com.Profpost.service.impl;

import com.Profpost.exception.ResourceNotFoundExcept;
import com.Profpost.model.entity.Creator;
import com.Profpost.repository.CreatorRepository;
import com.Profpost.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepository creatorRepository;

    @Override
    public Integer findCreatorIdByUserId(Integer userId) {
        Creator creator = creatorRepository.findByUserId(userId);
        if (creator == null) {
            throw new ResourceNotFoundExcept("El usuario no tiene un perfil de creador.");
        }
        return creator.getId();
    }
}
