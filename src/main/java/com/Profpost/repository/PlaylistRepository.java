package com.Profpost.repository;

import com.Profpost.model.entity.Playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository <Playlist, Integer> {
    Optional<Playlist> findByName(String name);
    // Buscar por nombre y usuario
    Optional<Playlist> findByNameAndUserId(String name, Integer userId);
    List<Playlist> findByUserId(Integer userId);  // Buscar por user_id
}
