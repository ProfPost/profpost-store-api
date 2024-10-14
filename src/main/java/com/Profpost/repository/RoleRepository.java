package com.Profpost.repository;

import com.Profpost.model.entity.Role;
import com.Profpost.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    //Buscar un rol por su nombre
    Optional<Role> findByName(ERole name);
}
