package com.Profpost.service;

import com.Profpost.model.entity.Playlist;
import java.util.List;

public interface PlaylistService {
    List<Playlist> findAll();
    Playlist create(Playlist playlist);
    Playlist findById(Integer id);
    Playlist update(Integer id, Playlist updatedPlaylist);
    void delete(Integer id);
}
