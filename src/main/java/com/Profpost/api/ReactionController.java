package com.Profpost.api;

import com.Profpost.dto.ReactionCreateDTO;
import com.Profpost.dto.ReactionDetailsDTO;
import com.Profpost.service.ReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/publication/reactions")

public class ReactionController {
    private final ReactionService reactionService;

    @GetMapping
    public ResponseEntity<List<ReactionDetailsDTO>> list(){
        List<ReactionDetailsDTO> reactions = reactionService.getAll();
        return new ResponseEntity<>(reactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReactionDetailsDTO> Create(@Valid @RequestBody ReactionCreateDTO reactionCreateDTO){
        ReactionDetailsDTO createreaction = reactionService.Create(reactionCreateDTO);
        return new ResponseEntity<>(createreaction, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id){
        reactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
