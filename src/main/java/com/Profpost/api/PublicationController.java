package com.Profpost.api;

import com.Profpost.dto.PublicationCreateDTO;
import com.Profpost.dto.PublicationDetailsDTO;
import com.Profpost.service.PublicationService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<PublicationDetailsDTO>> list() {
        List<PublicationDetailsDTO> publications = publicationService.findAll();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDetailsDTO> get(@PathVariable Integer id) {
        PublicationDetailsDTO publication = publicationService.findById(id);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @PostMapping("/creators")
    public ResponseEntity<PublicationDetailsDTO> create(@Valid @RequestBody PublicationCreateDTO publicationCreateDTO) {
        PublicationDetailsDTO createdPublication = publicationService.create(publicationCreateDTO);
        return new ResponseEntity<>(createdPublication, HttpStatus.CREATED);
    }

    @PutMapping("/creators/{id}")
    public ResponseEntity<PublicationDetailsDTO> update(@PathVariable Integer id, @Valid @RequestBody PublicationCreateDTO publicationCreateDTO) {
        PublicationDetailsDTO updatedPublication = publicationService.update(id, publicationCreateDTO);
        return new ResponseEntity<>(updatedPublication, HttpStatus.OK);
    }
    
    @DeleteMapping("/creators/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        publicationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
