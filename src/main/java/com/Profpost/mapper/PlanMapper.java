package com.Profpost.mapper;

import com.Profpost.dto.PlanDTO;
import com.Profpost.model.entity.Plan;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    private final ModelMapper modelMapper;
    public PlanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PlanDTO toPlanDTO(Plan plan) {
        PlanDTO planDTO = modelMapper.map(plan, PlanDTO.class);
        return planDTO;
    }

    public Plan toPlanEntity(PlanDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Plan.class);
    }
}
