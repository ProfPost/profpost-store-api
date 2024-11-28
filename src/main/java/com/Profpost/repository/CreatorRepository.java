package com.Profpost.repository;

import com.Profpost.model.entity.Creator;
import com.Profpost.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Integer> {
    Optional<Creator> findByUser(User user);
    boolean existsByName(String name);
    boolean existsByNameAndUserIdNot(String name, Integer userId);
    Creator findByUserId(Integer userId);
}
