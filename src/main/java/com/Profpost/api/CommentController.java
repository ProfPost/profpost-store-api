package com.Profpost.api;

import com.Profpost.dto.CommentCreateDTO;
import com.Profpost.dto.CommentDetailsDTO;
import com.Profpost.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/publication/comments")

public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDetailsDTO>> list(){
        List<CommentDetailsDTO> comments = commentService.getAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDetailsDTO> Create(@Valid @RequestBody CommentCreateDTO commentCreateDTO){
        CommentDetailsDTO createcomment = commentService.Create(commentCreateDTO);
        return new ResponseEntity<>(createcomment, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id){
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
