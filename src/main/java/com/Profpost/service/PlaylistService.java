package com.Profpost.service;

import com.Profpost.dto.PlaylistDTO;
import com.Profpost.model.entity.Publication;

import java.util.List;

public interface PlaylistService {
    List<PlaylistDTO> findAll();
    PlaylistDTO create(PlaylistDTO playlistDTO);
    PlaylistDTO findById(Integer id);

    PlaylistDTO update(Integer id, PlaylistDTO updatedPlaylistDTO);

    void delete(Integer id);

    List<Publication> getPublicationByPlaylistId(Integer playlistId);

    Publication addPublicationToPlaylist(Integer playlistId, Integer publicationId);

    void removePublicationFromPlaylist(Integer playlistId, Integer publicationId);

    // Nuevo método para obtener las playlists del usuario autenticado
    List<PlaylistDTO> findPlaylistsByUser();
}