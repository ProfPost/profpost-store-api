package com.Profpost.service;

import com.Profpost.model.entity.Playlist;
import com.Profpost.model.entity.Video;

import java.util.List;

public interface PlaylistService {
    List<Playlist> findAll();
    Playlist create(Playlist playlist);
    Playlist findById(Integer id);

    Playlist update(Integer id, Playlist updatedPlaylist);

    void delete(Integer id);

    List<Video> getVideosByPlaylistId(Integer playlistId);

    Video addVideoToPlaylist(Integer playlistId, Integer videoId);

    void removeVideoFromPlaylist(Integer playlistId, Integer videoId);
}