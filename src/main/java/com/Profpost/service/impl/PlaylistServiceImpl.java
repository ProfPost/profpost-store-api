package com.Profpost.service.impl;

import com.Profpost.dto.PlaylistDTO;
import com.Profpost.exception.BadRequestException;
import com.Profpost.exception.ResourceNotFoundExcept;

import com.Profpost.mapper.PlaylistMapper;
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
    private final PlaylistMapper playlistMapper;

    @Transactional(readOnly = true)
    @Override
    public List<PlaylistDTO> findAll() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(playlistMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public PlaylistDTO create(PlaylistDTO playlistDTO) {
        playlistRepository.findByName(playlistDTO.getName())
                        .ifPresent(existingPlaylist -> {
                            throw new BadRequestException("Playlist con nombre " + playlistDTO.getName() + " ya existe");
                        });
        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        playlist.setCreated_at(LocalDateTime.now());
        playlist=playlistRepository.save(playlist);
        return playlistMapper.toDTO(playlist);
    }

    @Transactional
    @Override
    public PlaylistDTO findById(Integer id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("Playlist con ID " +id+ "no encontrada"));
        return playlistMapper.toDTO(playlist);
    }

    @Transactional
    @Override
    public PlaylistDTO update(Integer id, PlaylistDTO updatedPlaylistDTO) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("La Playlist con ID " +id+ "no encontrada"));

        playlistRepository.findByName(updatedPlaylistDTO.getName())
                        .filter(existingPlaylist -> !existingPlaylist.getName().equals(id))
                        .ifPresent(existingPlaylist -> {throw new BadRequestException("Ya existe otra playlist con el mismo nombre");
                        });

        playlist.setName(updatedPlaylistDTO.getName());
        playlist.setUpdated_at(LocalDateTime.now());
        playlist=playlistRepository.save(playlist);
        return playlistMapper.toDTO(playlist);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Playlist playlist = playlistRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundExcept("Playlist con ID "+id+" no encontrada"));
        playlistRepository.delete(playlist);
    }

    @Transactional
    @Override
    public List<Publication> getPublicationByPlaylistId(Integer playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundExcept("Playlist no encontrada"));
        return playlist.getPublications();
    }

    @Transactional
    @Override
    public Publication addPublicationToPlaylist(Integer playlistId, Integer publicationId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundExcept("Playlist no encontrada"));
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcept("Publicación no encontrada"));
        publication.setPlaylist(playlist);
        publication.setUpdatedAt(LocalDateTime.now());
        return publicationRepository.save(publication);
    }

    @Transactional
    @Override
    public void removePublicationFromPlaylist(Integer playlistId, Integer publicationId) {
        playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundExcept("Playlist no encontrada"));
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcept("Publicación no encontrada"));
        publication.setPlaylist(null);
        publicationRepository.save(publication);
    }
}