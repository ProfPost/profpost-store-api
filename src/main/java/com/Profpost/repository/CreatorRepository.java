package com.Profpost.repository;

import com.Profpost.model.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Integer> {
    Optional<Creator> findByName(String name);

    boolean existsByName(String name);

    //Metodo para verificar si ya existe un creador con el mismo nombre y apellido, excepto el usuario actual
    boolean existsByNameAnAndUserIdNot(String name, Integer userId);
}
