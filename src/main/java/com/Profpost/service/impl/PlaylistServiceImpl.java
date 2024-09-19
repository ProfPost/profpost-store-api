package com.Profpost.service.impl;

import com.Profpost.model.entity.Playlist;
import com.Profpost.model.entity.Video;
import com.Profpost.repository.PlaylistRepository;
import com.Profpost.repository.VideoRepository;
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
    private final VideoRepository videoRepository;

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
    public List<Video> getVideosByPlaylistId(Integer playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        return playlist.getVideos();
    }

    @Transactional
    @Override
    public Video addVideoToPlaylist(Integer playlistId, Integer videoId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        video.setPlaylist(playlist);
        video.setUpdatedAt(LocalDateTime.now());
        return videoRepository.save(video);
    }

    @Transactional
    @Override
    public void removeVideoFromPlaylist(Integer playlistId, Integer videoId) {
        playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        video.setPlaylist(null);
        videoRepository.save(video);
    }
}