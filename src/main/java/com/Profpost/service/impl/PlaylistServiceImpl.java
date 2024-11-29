package com.Profpost.service.impl;

import com.Profpost.dto.PlaylistDTO;
import com.Profpost.exception.BadRequestException;
import com.Profpost.exception.ResourceNotFoundExcept;

import com.Profpost.mapper.PlaylistMapper;
import com.Profpost.model.entity.Playlist;
import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.User;
import com.Profpost.repository.PlaylistRepository;
import com.Profpost.repository.PublicationRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;

    @Transactional
    @Override
    public List<PlaylistDTO> findPlaylistsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Playlist> playlists = playlistRepository.findByUserId(user.getId());
        return playlists.stream()
                .map(playlistMapper::toDTO)
                .toList();
    }

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        playlistRepository.findByNameAndUserId(playlistDTO.getName(), user.getId())
                .ifPresent(existingPlaylist -> {
                    throw new BadRequestException("Ya existe una playlist con el nombre '" + playlistDTO.getName() + "' para este usuario.");
                });

        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        playlist.setUser(user);
        playlist.setCreated_at(LocalDateTime.now());
        playlist = playlistRepository.save(playlist);
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
                .orElseThrow(() -> new ResourceNotFoundExcept("La Playlist con ID " + id + " no encontrada"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!playlist.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para actualizar esta playlist");
        }

        // Verificar si ya existe una playlist con el mismo nombre, pero diferente ID
        playlistRepository.findByName(updatedPlaylistDTO.getName())
                .filter(existingPlaylist -> !existingPlaylist.getId().equals(id))
                .ifPresent(existingPlaylist -> {
                    throw new BadRequestException("Ya existe otra playlist con el mismo nombre");
                });

        playlist.setName(updatedPlaylistDTO.getName());
        playlist.setUpdated_at(LocalDateTime.now());
        playlist = playlistRepository.save(playlist);
        return playlistMapper.toDTO(playlist);
    }


    @Transactional
    @Override
    public void delete(Integer id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("Playlist con ID " + id + " no encontrada"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!playlist.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para eliminar esta playlist");
        }
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