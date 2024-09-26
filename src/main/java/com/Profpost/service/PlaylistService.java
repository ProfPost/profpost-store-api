package com.Profpost.service;

import com.Profpost.model.entity.Playlist;
import com.Profpost.model.entity.Publication;

import java.util.List;

public interface PlaylistService {
    List<Playlist> findAll();
    Playlist create(Playlist playlist);
    Playlist findById(Integer id);

    Playlist update(Integer id, Playlist updatedPlaylist);

    void delete(Integer id);

    List<Publication> getPublicationByPlaylistId(Integer playlistId);

    Publication addPublicationToPlaylist(Integer playlistId, Integer publicationId);

    void removePublicationFromPlaylist(Integer playlistId, Integer publicationId);
}