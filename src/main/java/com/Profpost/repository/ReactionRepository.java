package com.Profpost.repository;

import com.Profpost.model.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    List<Reaction> findByUser_Id(Integer userId);
}
