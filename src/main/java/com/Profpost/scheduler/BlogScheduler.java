package com.Profpost.scheduler;

import com.Profpost.model.entity.Blog;
import com.Profpost.repository.BlogRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BlogScheduler {
    private final BlogRepository blogRepository;

    public BlogScheduler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void publishScheduledBlogs() {
        List<Blog> scheduledBlogs = blogRepository.findAllByIsPublishedFalseAndSchedulePublishAtBefore(LocalDateTime.now());

        for (Blog blog : scheduledBlogs) {
            blog.setPublished(true);
            blogRepository.save(blog);
        }
    }
}
