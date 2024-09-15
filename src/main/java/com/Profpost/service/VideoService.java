package com.Profpost.service;

import com.Profpost.model.entity.Video;
import java.time.LocalDateTime;
import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video findById(Integer id);
    Video create(Video video);
    Video update(Integer id, Video updateVideo);
    void delete(Integer id);
}
