package com.Profpost.repository;

import com.Profpost.model.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository <Playlist, Integer> {
}
