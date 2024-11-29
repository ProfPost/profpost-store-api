package com.Profpost.api;

import com.Profpost.dto.PublicationInPlaylistDTO;
import com.Profpost.dto.PlaylistDTO;
import com.Profpost.model.entity.Publication;
import com.Profpost.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/playlist")
@PreAuthorize("hasAnyRole('ADMIN', 'CREATOR', 'READER')")

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
    public ResponseEntity<List<PublicationInPlaylistDTO>> getPublicationsForPlaylist(@PathVariable Integer playlistId) {
        List<PublicationInPlaylistDTO> publications = playlistService.getPublicationsForPlaylist(playlistId);
        return ResponseEntity.ok(publications);
    }

    @PostMapping("/add/publication")
    public ResponseEntity<Publication> addPublicationToPlaylist(@RequestParam Integer playlistId, @RequestParam Integer publicationId) {
        Publication updatedpublication = playlistService.addPublicationToPlaylist(playlistId, publicationId);
        return ResponseEntity.ok(updatedpublication);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{playlistId}/publications/{publicationId}")
    public void removePublicationFromPlaylist(
            @PathVariable Integer playlistId,
            @PathVariable Integer publicationId) {
        playlistService.removePublicationFromPlaylist(playlistId, publicationId);
    }

    @GetMapping("/my-playlists")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsForUser() {
        List<PlaylistDTO> playlists = playlistService.findPlaylistsByUser();
        return ResponseEntity.ok(playlists);
    }
}
