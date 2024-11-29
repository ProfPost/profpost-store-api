package com.Profpost.service;

import com.Profpost.dto.PlaylistDTO;
import com.Profpost.dto.PublicationInPlaylistDTO;
import com.Profpost.model.entity.Publication;

import java.util.List;

public interface PlaylistService {
    List<PlaylistDTO> findAll();
    PlaylistDTO create(PlaylistDTO playlistDTO);
    PlaylistDTO findById(Integer id);

    PlaylistDTO update(Integer id, PlaylistDTO updatedPlaylistDTO);

    void delete(Integer id);

    public List<PublicationInPlaylistDTO> getPublicationsForPlaylist(Integer playlistId);

    Publication addPublicationToPlaylist(Integer playlistId, Integer publicationId);

    void removePublicationFromPlaylist(Integer playlistId, Integer publicationId);

    List<PlaylistDTO> findPlaylistsByUser();
}

