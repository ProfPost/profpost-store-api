package com.Profpost.api;

import com.Profpost.dto.PublicationDTO;
import com.Profpost.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/publications")
@PreAuthorize("hasAnyRole('ADMIN', 'CREATOR')")

public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping
    public ResponseEntity<List<PublicationDTO>> list() {
        List<PublicationDTO> publications = publicationService.findAll();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> get(@PathVariable Integer id) {
        PublicationDTO publication = publicationService.findById(id);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @PostMapping("/creators")
    public ResponseEntity<PublicationDTO> create(@RequestBody PublicationDTO publicationDTO) {
        PublicationDTO createdPublication = publicationService.create(publicationDTO);
        return new ResponseEntity<>(createdPublication, HttpStatus.CREATED);
    }

    @PutMapping("/creators/{id}")
    public ResponseEntity<PublicationDTO> update(@PathVariable Integer id, @RequestBody PublicationDTO publicationDTO) {
        PublicationDTO updatedPublication = publicationService.update(id, publicationDTO);
        return new ResponseEntity<>(updatedPublication, HttpStatus.OK);
    }
    
    @DeleteMapping("/creators/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        publicationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
