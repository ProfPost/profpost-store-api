package com.Profpost.api;

import com.Profpost.model.entity.Plan;
import com.Profpost.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")

public class PlanController {
    private final PlanService planService;

    @GetMapping
    public ResponseEntity<List<Plan>>listPlan(){
        List<Plan> blogs = planService.findAll();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Plan> create(@RequestBody Plan plan){
        Plan createdPlan = planService.create(plan);
        return new ResponseEntity<>(createdPlan,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getById(@PathVariable Integer id){
        Plan plan = planService.findById(id);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> update(@PathVariable Integer id, @RequestBody Plan plan){
        Plan updatePlan = planService.update(id,plan);
        return new ResponseEntity<>(updatePlan,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        planService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
