package com.Profpost.service.impl;

import com.Profpost.dto.PlanDTO;
import com.Profpost.exception.ResourceNotFoundException;
import com.Profpost.exception.BadRequestException;
import com.Profpost.mapper.PlanMapper;
import com.Profpost.model.entity.Plan;
import com.Profpost.repository.PlanRepository;
import com.Profpost.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service

public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    @Transactional(readOnly = true)
    @Override
    public List<PlanDTO> findAll() {
        List<Plan> plans = planRepository.findAll();
        return plans.stream()
                .map(planMapper::toPlanDTO)
                .toList();
    }

    @Transactional
    @Override
    public PlanDTO create(PlanDTO planDTO) {
        planRepository.findByName(planDTO.getName())
                .ifPresent(existingPlan -> {
                    throw new BadRequestException("Plan already exists");
                });
        Plan plan = planMapper.toPlanEntity(planDTO);
        Plan savedPlan = planRepository.save(plan);
        return planMapper.toPlanDTO(savedPlan);
    }

    @Transactional(readOnly = true)
    @Override
    public PlanDTO findById(Integer id) {
        Plan plan = planRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EL Plan with Id " +id+ "not found"));
        return planMapper.toPlanDTO(plan);
    }

    @Transactional
    @Override
    public PlanDTO update(Integer id, PlanDTO planDTO) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EL Plan with Id " +id+ "not found"));
        planRepository.findByName(planDTO.getName())
                .ifPresent(existingPlan -> {
                    throw new BadRequestException("Ya existe un plan con el mismo nombre");
                });

        plan.setName(planDTO.getName());
        plan.setPrice(planDTO.getPrice());
        plan.setDescription(planDTO.getDescription());

        Plan updatedPlan = planRepository.save(plan);
        return planMapper.toPlanDTO(updatedPlan);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EL Plan with Id " +id+ "not found"));
        planRepository.delete(plan);
    }

}