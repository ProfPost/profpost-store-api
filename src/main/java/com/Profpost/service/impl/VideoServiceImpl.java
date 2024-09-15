package com.Profpost.service.impl;

import com.Profpost.model.entity.Video;
import com.Profpost.repository.VideoRepository;
import com.Profpost.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service

public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Transactional
    @Override
    public Video create(Video video) {
        video.setCreatedAt(LocalDateTime.now());
        return videoRepository.save(video);
    }

    @Transactional
    @Override
    public Video findById(Integer id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }

    @Transactional
    @Override
    public Video update(Integer id, Video updatedVideo) {
        Video videoFromDb = findById(id);
        videoFromDb.setTitle(updatedVideo.getTitle());
        videoFromDb.setUrl(updatedVideo.getUrl());
        videoFromDb.setDuration(updatedVideo.getDuration());
        videoFromDb.setCoverPath(updatedVideo.getCoverPath());
        videoFromDb.setFilePath(updatedVideo.getFilePath());
        videoFromDb.setUpdatedAt(LocalDateTime.now());
        return videoRepository.save(videoFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Video video = findById(id);
        videoRepository.delete(video);
    }

}