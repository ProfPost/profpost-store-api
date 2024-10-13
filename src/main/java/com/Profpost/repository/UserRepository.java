package com.Profpost.repository;

import com.Profpost.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    //Metodo para buscar un usuario por email (sera usado en la autenticacion)
    Optional<User> findByEmail(String email);
}
