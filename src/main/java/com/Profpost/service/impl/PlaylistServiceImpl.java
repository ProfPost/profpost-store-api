package com.Profpost.service.impl;

import com.Profpost.model.entity.Playlist;
import com.Profpost.repository.PlaylistRepository;
import com.Profpost.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository PlaylistRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Playlist> findAll() {
        return PlaylistRepository.findAll();
    }

    @Transactional
    @Override
    public Playlist create(Playlist playlist) {
        playlist.setCreated_at(LocalDateTime.now());
        return PlaylistRepository.save(playlist);
    }

    @Transactional
    @Override
    public Playlist findById(Integer id) {
        return PlaylistRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Playlist not found"));
    }

    @Transactional
    @Override
    public Playlist update(Integer id, Playlist updatedPlaylist) {
        Playlist playlistFromDb = findById(id);
        playlistFromDb.setName(updatedPlaylist.getName());
        playlistFromDb.setUpdated_at(LocalDateTime.now());
        return PlaylistRepository.save(playlistFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Playlist playlist = findById(id);
        PlaylistRepository.delete(playlist);
    }
}
