package com.Profpost.service;

import com.Profpost.model.entity.Video;
import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video create(Video video);
    Video findById(Integer id);
    Video update(Integer id, Video updateVideo);
    void delete(Integer id);
}