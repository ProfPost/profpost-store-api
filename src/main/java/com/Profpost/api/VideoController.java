package com.Profpost.api;

import com.Profpost.dto.VideoDTO;
import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.User;
import com.Profpost.model.entity.Video;
import com.Profpost.model.enums.Role;
import com.Profpost.service.CategoryService;
import com.Profpost.service.UserService;
import com.Profpost.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")

public class VideoController {
    private final VideoService videoService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Video>> listVideo() {
        List<Video> video = videoService.findAll();
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getById(@PathVariable Integer id) {
        Video video = videoService.findById(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @PostMapping("/creators")
    public ResponseEntity<Video> create(@RequestBody VideoDTO videoDTO) {
        User user = userService.findById(videoDTO.getUser_id());
        Category category = categoryService.findById(videoDTO.getCategory_id());
        if (user.getRole() != Role.CREATOR){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Video video = videoService.create(videoDTO.toVideo(category, user));

        return new ResponseEntity<>(video, HttpStatus.CREATED);
    }

    @PutMapping("/creators/{id}")
    public ResponseEntity<Video> update(@PathVariable Integer id, @RequestBody VideoDTO videoDTO) {
        Video existingVideo = videoService.findById(id);

        if (videoDTO.getCategory_id() != null) {
            Category category = categoryService.findById(videoDTO.getCategory_id());
            existingVideo.setCategory(category);
        }

        existingVideo.setTitle(videoDTO.getTitle());
        existingVideo.setUrl(videoDTO.getUrl());
        existingVideo.setSchedulePublishAt(videoDTO.getSchedulePublishAt());
        existingVideo.setUpdatedAt(existingVideo.getUpdatedAt());

        Video updateVideo = videoService.update(id, existingVideo);
        return new ResponseEntity<>(updateVideo, HttpStatus.OK);
    }

    @DeleteMapping("/creators/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        videoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
