package com.Profpost.api;

import com.Profpost.model.entity.Playlist;
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
    public Playlist update(@PathVariable Integer id, @RequestBody Playlist playlist) {
        return playlistService.update(id, playlist);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        playlistService.delete(id);
    }
}
