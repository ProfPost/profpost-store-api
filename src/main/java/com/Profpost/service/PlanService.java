package com.Profpost.service;

import com.Profpost.model.entity.Plan;
import java.util.List;

public interface PlanService {
    List<Plan> findAll();
    Plan create(Plan plan);
    Plan findById(Integer id);
    Plan update(Integer id, Plan updatedPlan);
    void delete(Integer id);
}