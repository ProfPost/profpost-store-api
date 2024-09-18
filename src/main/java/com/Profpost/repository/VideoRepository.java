package com.Profpost.repository;

import com.Profpost.model.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findAllByIsPublishedFalseAndSchedulePublishAtBefore(LocalDateTime now);
}