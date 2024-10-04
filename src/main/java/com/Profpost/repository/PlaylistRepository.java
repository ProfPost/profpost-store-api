package com.Profpost.repository;

import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository <Playlist, Integer> {
    Optional<Playlist> findByName(String name);
}
