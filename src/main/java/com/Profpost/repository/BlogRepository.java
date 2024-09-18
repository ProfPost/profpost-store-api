package com.Profpost.repository;

import com.Profpost.model.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findAllByIsPublishedFalseAndSchedulePublishAtBefore(LocalDateTime now);
}
