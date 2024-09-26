package com.Profpost.service.impl;

import com.Profpost.model.entity.Playlist;
import com.Profpost.model.entity.Publication;
import com.Profpost.repository.PlaylistRepository;
import com.Profpost.repository.PublicationRepository;
import com.Profpost.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PublicationRepository publicationRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Transactional
    @Override
    public Playlist create(Playlist playlist) {
        playlist.setCreated_at(LocalDateTime.now());
        return playlistRepository.save(playlist);
    }

    @Transactional
    @Override
    public Playlist findById(Integer id) {
        return playlistRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Playlist not found"));
    }

    @Transactional
    @Override
    public Playlist update(Integer id, Playlist updatedPlaylist) {
        Playlist playlistFromDb = findById(id);
        playlistFromDb.setName(updatedPlaylist.getName());
        playlistFromDb.setUpdated_at(LocalDateTime.now());
        return playlistRepository.save(playlistFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Playlist playlist = findById(id);
        playlistRepository.delete(playlist);
    }

    @Transactional
    @Override
    public List<Publication> getPublicationByPlaylistId(Integer playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        return playlist.getPublications();
    }

    @Transactional
    @Override
    public Publication addPublicationToPlaylist(Integer playlistId, Integer publicationId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        publication.setPlaylist(playlist);
        publication.setUpdatedAt(LocalDateTime.now());
        return publicationRepository.save(publication);
    }

    @Transactional
    @Override
    public void removePublicationFromPlaylist(Integer playlistId, Integer publicationId) {
        playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        publication.setPlaylist(null);
        publicationRepository.save(publication);
    }
}