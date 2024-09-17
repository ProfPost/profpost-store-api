package com.Profpost.api;

import com.Profpost.model.entity.Video;
import com.Profpost.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/videos")

public class VideoController {
    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<Video>> listVideo() {
        return ResponseEntity.ok(videoService.findAll());
    }

    @PostMapping
    public ResponseEntity<Video> create(@RequestBody Video video) {
        Video createdVideo = videoService.create(video);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getById(@PathVariable Integer id) {
        Video video = videoService.findById(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> update(@PathVariable Integer id, @RequestBody Video video) {
        Video updateVideo = videoService.update(id, video);
        return new ResponseEntity<>(updateVideo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        videoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}