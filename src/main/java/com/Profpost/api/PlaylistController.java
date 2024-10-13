package com.Profpost.api;

import com.Profpost.dto.PlaylistDTO;
import com.Profpost.model.entity.Publication;
import com.Profpost.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/playlist")

public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> listUser(){
        return ResponseEntity.ok(playlistService.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PlaylistDTO create(@RequestBody PlaylistDTO playlistDTO) {
        return playlistService.create(playlistDTO);
    }

    @GetMapping("/{id}")
    public PlaylistDTO get(@PathVariable Integer id) {
        return playlistService.findById(id);
    }

    @PutMapping("/{id}")
    public PlaylistDTO update(@PathVariable Integer id, @RequestBody PlaylistDTO playlistDTO) {
        return playlistService.update(id, playlistDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        playlistService.delete(id);
    }

    @GetMapping("/{playlistId}/publications")
    public ResponseEntity<List<Publication>> getPublicationByPlaylist(@PathVariable Integer playlistId) {
        List<Publication> publication = playlistService.getPublicationByPlaylistId(playlistId);
        return ResponseEntity.ok(publication);
    }

    @PostMapping("/add/publication")
    public ResponseEntity<Publication> addPublicationToPlaylist(@RequestParam Integer playlistId, @RequestParam Integer publicationId) {
        Publication updatedpublication = playlistService.addPublicationToPlaylist(playlistId, publicationId);
        return ResponseEntity.ok(updatedpublication);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{playlistId}/publications/{publicationsId}")
    public ResponseEntity<Void> removePublicationFromPlaylist(@PathVariable Integer playlistId, @PathVariable Integer publicationId) {
        playlistService.removePublicationFromPlaylist(playlistId, publicationId);
        return ResponseEntity.noContent().build();
    }
}
