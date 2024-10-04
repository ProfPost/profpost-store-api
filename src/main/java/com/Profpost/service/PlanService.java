package com.Profpost.service;

import com.Profpost.dto.PlanDTO;
import com.Profpost.model.entity.Plan;
import java.util.List;

public interface PlanService {
    List<PlanDTO> findAll();
    PlanDTO create(PlanDTO planDTO);
    PlanDTO findById(Integer id);
    PlanDTO update(Integer id, PlanDTO planDTO);
    void delete(Integer id);
}