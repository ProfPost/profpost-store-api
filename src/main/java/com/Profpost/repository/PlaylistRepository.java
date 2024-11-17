package com.Profpost.repository;

import com.Profpost.model.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository <Playlist, Integer> {
    Optional<Playlist> findByName(String name);
}
