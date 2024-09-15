package com.Profpost.service.impl;

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

    @Transactional(readOnly = true)
    @Override
    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @Transactional
    @Override
    public Plan create(Plan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    @Override
    public Plan findById(Integer id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    @Transactional
    @Override
    public Plan update(Integer id, Plan updatedPlan) {
        Plan planFromDb = findById(id);
        planFromDb.setName(updatedPlan.getName());
        planFromDb.setPrice(updatedPlan.getPrice());
        planFromDb.setDescription(updatedPlan.getDescription());
        return planRepository.save(planFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Plan plan = findById(id);
        planRepository.delete(plan);
    }

}