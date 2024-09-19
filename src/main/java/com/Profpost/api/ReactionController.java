package com.Profpost.api;

import com.Profpost.model.entity.Reaction;
import com.Profpost.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reaction")
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity<Reaction> createReaction(@RequestBody Reaction reaction) {
        Reaction createdReaction = reactionService.save(reaction);
        return new ResponseEntity<>(createdReaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reaction> getReactionById(@PathVariable Integer id) {
        Reaction reaction = reactionService.findById(id);
        return new ResponseEntity<>(reaction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Integer id) {
        reactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Reaction>> listReactions() {
        List<Reaction> reactions = reactionService.findAll();
        return new ResponseEntity<>(reactions, HttpStatus.OK);
    }
}
