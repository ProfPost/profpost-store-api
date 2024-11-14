package com.Profpost.api;

import com.Profpost.dto.PublicationDetailsDTO;
import com.Profpost.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class HomePublicationController {
    private final PublicationService publicationService;

    @GetMapping("/recent")
    public ResponseEntity<List<PublicationDetailsDTO>> getRecentBooks(){
        List<PublicationDetailsDTO> recentPublications = publicationService.findTop5PublicationsByCreatedAt();
        return new ResponseEntity<>(recentPublications, HttpStatus.OK);
    }
}