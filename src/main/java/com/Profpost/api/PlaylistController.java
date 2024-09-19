package com.Profpost.api;

import com.Profpost.model.entity.Playlist;
import com.Profpost.model.entity.Video;
import com.Profpost.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/playlist")

public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<Playlist>> listUser(){
        return ResponseEntity.ok(playlistService.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Playlist create(@RequestBody Playlist playlist) {
        return playlistService.create(playlist);
    }

    @GetMapping("/{id}")
    public Playlist get(@PathVariable Integer id) {
        return playlistService.findById(id);
    }

    @PutMapping("/{id}")
    public Playlist update(@PathVariable Integer id, @RequestBody Playlist
            playlist) {
        return playlistService.update(id, playlist);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        playlistService.delete(id);
    }


    @GetMapping("/{playlistId}/videos")
    public ResponseEntity<List<Video>> getVideosByPlaylist(@PathVariable Integer playlistId) {
        List<Video> videos = playlistService.getVideosByPlaylistId(playlistId);
        return ResponseEntity.ok(videos);
    }

    @PostMapping("/add/video")
    public ResponseEntity<Video> addVideoToPlaylist(@RequestParam Integer playlistId, @RequestParam Integer videoId) {
        Video updatedVideo = playlistService.addVideoToPlaylist(playlistId, videoId);
        return ResponseEntity.ok(updatedVideo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{playlistId}/videos/{videoId}")
    public ResponseEntity<Void> removeVideoFromPlaylist(@PathVariable Integer playlistId, @PathVariable Integer videoId) {
        playlistService.removeVideoFromPlaylist(playlistId, videoId);
        return ResponseEntity.noContent().build();
    }
}